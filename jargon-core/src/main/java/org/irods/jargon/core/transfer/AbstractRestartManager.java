/**
 *
 */
package org.irods.jargon.core.transfer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract superclass for a manager of file restarts. This allows in-memory,
 * file based, and other variants to maintain long file restart information.
 * <p/>
 * This manager serves as a repository of file restart info and a place to
 * update that information, maintaining a representation of
 * {@link FileRestartInfo} for a given path
 *
 * @author Mike Conway - DICE
 *
 */
public abstract class AbstractRestartManager {

	private static final Logger log = LoggerFactory
			.getLogger(AbstractRestartManager.class);

	/**
	 * Either return existing, or create a new restart identifier
	 *
	 * @param fileRestartInfoIdentifier
	 * @param localFilePath
	 *            <code>String</code> with the local file name
	 * @return
	 * @throws FileRestartManagementException
	 */
	public synchronized FileRestartInfo retrieveRestartAndBuildIfNotStored(
			final FileRestartInfoIdentifier fileRestartInfoIdentifier,
			final String localFilePath, final int numberOfThreads)
					throws FileRestartManagementException {

		log.info("retrieveRestartAndBuildIfNotStored()");

		if (fileRestartInfoIdentifier == null) {
			throw new IllegalArgumentException("null fileRestartInfoIdentifier");
		}

		if (localFilePath == null || localFilePath.isEmpty()) {
			throw new IllegalArgumentException("null or empty localFilePath");
		}

		FileRestartInfo info = retrieveRestart(fileRestartInfoIdentifier);

		if (info == null) {
			log.info("no restart saved, create and store a new one");
			info = new FileRestartInfo();
			info.setIrodsAbsolutePath(fileRestartInfoIdentifier
					.getAbsolutePath());
			info.setIrodsAccountIdentifier(fileRestartInfoIdentifier
					.getIrodsAccountIdentifier());
			info.setRestartType(fileRestartInfoIdentifier.getRestartType());
			info.setLocalAbsolutePath(localFilePath);
			/*
			 * Add a segment for each row
			 */
			for (int i = 0; i < numberOfThreads; i++) {
				info.getFileRestartDataSegments().add(
						new FileRestartDataSegment(i));
			}
			storeRestart(info);
		}
		return info;
	}

	/**
	 * Method to properly update a segment, allowing the implementation to do
	 * synchronization of that update
	 *
	 * @param fileRestartInfo
	 *            {@link FileRestartInfo} that contains the segments
	 * @param fileRestartDataSegment
	 *            {@link FileRestartDataSegment} that contains the segment to
	 * @throws FileRestartMangementException
	 */
	public abstract void updateSegment(final FileRestartInfo fileRestartInfo,
			final FileRestartDataSegment fileRestartDataSegment)
					throws FileRestartManagementException;

	/**
	 * Method to properly increment the restart count, allowing an
	 * implementation to do synchronization. <code>null</code> is returned if no
	 * restart info is found.
	 *
	 * @param fileRestartInfo
	 *            {@link FileRestartInfo} that contains the attempt count
	 * @return FileRestartInfo
	 * @exception RestartFailedException
	 *                if the count is exceeded or the restart fails
	 * @throws FileRestartManagementException
	 */
	public abstract FileRestartInfo incrementRestartAttempts(
			final FileRestartInfo fileRestartInfo)
					throws RestartFailedException, FileRestartManagementException;

	/**
	 * Given an identifier and thread number, find the segment and update the
	 * length on that segment
	 *
	 * @param fileRestartInfoIdentifier
	 *            {@link FileRestartInfoIdentifier}
	 * @param threadNumber
	 *            <code>int</code> with the thread number
	 * @param length
	 *            <code>long</code> with the length to add to the segment
	 * @throws FileRestartManagementException
	 */
	public void updateLengthForSegment(
			final FileRestartInfoIdentifier fileRestartInfoIdentifier,
			final int threadNumber, final long length)
					throws FileRestartManagementException {
		log.info("updateLengthForSegment()");
		if (fileRestartInfoIdentifier == null) {
			throw new IllegalArgumentException("null identifier");
		}

		synchronized (this) {
			FileRestartInfo info = retrieveRestart(fileRestartInfoIdentifier);
			if (info == null) {
				throw new FileRestartManagementException(
						"unable to find restart info");
			}

			if (info.getFileRestartDataSegments().size() - 1 < threadNumber) {
				throw new FileRestartManagementException(
						"unable to locate thread number");
			}

			FileRestartDataSegment dataSegment = info
					.getFileRestartDataSegments().get(threadNumber);

			if (dataSegment.getThreadNumber() != threadNumber) {
				log.error(
						"thread number in segment odos not match requested:{}",
						threadNumber);
				log.error("segment was:{}", dataSegment);
				throw new FileRestartManagementException(
						"thread number mismatch");
			}

			dataSegment.setLength(dataSegment.getLength() + length);
			storeRestart(info);
		}

	}

	/**
	 * Given an identifier and thread number, find the segment and update the
	 * offset on that segment. Also sets the length to zero.
	 *
	 * @param fileRestartInfoIdentifier
	 *            {@link FileRestartInfoIdentifier}
	 * @param threadNumber
	 *            <code>int</code> with the thread number
	 * @param offset
	 *            <code>long</code> with the length to add to the segment
	 * @throws FileRestartManagementException
	 */
	public void updateOffsetForSegment(
			final FileRestartInfoIdentifier fileRestartInfoIdentifier,
			final int threadNumber, final long offset)
					throws FileRestartManagementException {
		log.info("updateLenghtForSegment()");
		if (fileRestartInfoIdentifier == null) {
			throw new IllegalArgumentException("null identifier");
		}

		synchronized (this) {
			FileRestartInfo info = retrieveRestart(fileRestartInfoIdentifier);
			if (info == null) {
				throw new FileRestartManagementException(
						"unable to find restart info");
			}

			if (info.getFileRestartDataSegments().size() - 1 < threadNumber) {
				throw new FileRestartManagementException(
						"unable to locate thread number");
			}

			FileRestartDataSegment dataSegment = info
					.getFileRestartDataSegments().get(threadNumber);
			dataSegment.setOffset(offset);
			dataSegment.setLength(0);
			storeRestart(info);
		}

	}

	/**
	 * Store the restart information
	 *
	 * @param fileRestartInfo
	 *            {@link FileRestartInfo} to be stored
	 * @return {@link FileRestartInfoIdentifier} that is the derived key from
	 *         the restart info
	 * @throws FileRestartManagementException
	 */
	public abstract FileRestartInfoIdentifier storeRestart(
			final FileRestartInfo fileRestartInfo)
					throws FileRestartManagementException;

	/**
	 * Delete the file restart information
	 *
	 * @param fileRestartInfoIdentifier
	 * @return {@link FileRestartInfoIdentifier} that is the key (oper type,
	 *         account info, path)
	 * @throws FileRestartManagementException
	 */
	public abstract void deleteRestart(
			final FileRestartInfoIdentifier fileRestartInfoIdentifier)
					throws FileRestartManagementException;

	/**
	 * Retrieve the file restart information given the identifying information
	 *
	 * @param fileRestartInfoIdentifier
	 *            {@link FileRestartInfoIdentifier} that is the key (oper type,
	 *            account info, path)
	 * @return {@link FileRestartInfo} that matches the key or <code>null</code>
	 *         if no match
	 * @throws FileRestartManagementException
	 *
	 */
	public abstract FileRestartInfo retrieveRestart(
			final FileRestartInfoIdentifier fileRestartInfoIdentifier)
					throws FileRestartManagementException;

}
