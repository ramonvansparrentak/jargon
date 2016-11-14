package org.irods.jargon.core.pub.domain;

import java.util.Date;

import org.irods.jargon.core.query.CollectionAndDataObjectListingEntry;
import org.irods.jargon.core.query.CollectionAndDataObjectListingEntry.ObjectType;

/**
 * Represents an object stat value as returned from the iRODS rsObjStat. NOTE:
 * work in progress, subject to change
 *
 * @author Mike Conway - DICE (www.irods.org)
 *
 */
public class ObjStat {

	private String absolutePath = "";
	private String objectPath = "";
	private CollectionAndDataObjectListingEntry.ObjectType objectType;
	private int dataId = 0;
	private String checksum = "";
	private String ownerName = "";
	private String ownerZone = "";
	private long objSize = 0L;
	private Date createdAt = null;
	private Date modifiedAt = null;
	private SpecColType specColType = SpecColType.NORMAL;
	private String collectionPath = "";
	private String cacheDir = "";
	private boolean cacheDirty = false;
	private int replNumber = 0;
	/**
	 * ObjStats may at times be generated by heuristic when strictACLs preclude
	 * lookup. In this case, the stand-ins are marked
	 */
	private boolean standInGeneratedObjStat = false;

	public enum SpecColType {
		NORMAL, STRUCT_FILE_COLL, MOUNTED_COLL, LINKED_COLL
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ObjStat [");
		if (absolutePath != null) {
			builder.append("absolutePath=");
			builder.append(absolutePath);
			builder.append(", ");
		}
		if (objectPath != null) {
			builder.append("objectPath=");
			builder.append(objectPath);
			builder.append(", ");
		}
		if (objectType != null) {
			builder.append("objectType=");
			builder.append(objectType);
			builder.append(", ");
		}
		builder.append("dataId=");
		builder.append(dataId);
		builder.append(", ");
		if (checksum != null) {
			builder.append("checksum=");
			builder.append(checksum);
			builder.append(", ");
		}
		if (ownerName != null) {
			builder.append("ownerName=");
			builder.append(ownerName);
			builder.append(", ");
		}
		if (ownerZone != null) {
			builder.append("ownerZone=");
			builder.append(ownerZone);
			builder.append(", ");
		}
		builder.append("objSize=");
		builder.append(objSize);
		builder.append(", ");
		if (createdAt != null) {
			builder.append("createdAt=");
			builder.append(createdAt);
			builder.append(", ");
		}
		if (modifiedAt != null) {
			builder.append("modifiedAt=");
			builder.append(modifiedAt);
			builder.append(", ");
		}
		if (specColType != null) {
			builder.append("specColType=");
			builder.append(specColType);
			builder.append(", ");
		}
		if (collectionPath != null) {
			builder.append("collectionPath=");
			builder.append(collectionPath);
			builder.append(", ");
		}
		if (cacheDir != null) {
			builder.append("cacheDir=");
			builder.append(cacheDir);
			builder.append(", ");
		}
		builder.append("cacheDirty=");
		builder.append(cacheDirty);
		builder.append(", replNumber=");
		builder.append(replNumber);
		builder.append(", standInGeneratedObjStat=");
		builder.append(standInGeneratedObjStat);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * @return the absolutePath
	 */
	public String getAbsolutePath() {
		return absolutePath;
	}

	/**
	 * @param absolutePath
	 *            the absolutePath to set
	 */
	public void setAbsolutePath(final String absolutePath) {
		this.absolutePath = absolutePath;
	}

	/**
	 * @return the objectType
	 */
	public CollectionAndDataObjectListingEntry.ObjectType getObjectType() {
		return objectType;
	}

	/**
	 * @param objectType
	 *            the objectType to set
	 */
	public void setObjectType(
			final CollectionAndDataObjectListingEntry.ObjectType objectType) {
		this.objectType = objectType;
	}

	/**
	 * @return the dataId
	 */
	public int getDataId() {
		return dataId;
	}

	/**
	 * @param dataId
	 *            the dataId to set
	 */
	public void setDataId(final int dataId) {
		this.dataId = dataId;
	}

	/**
	 * @return the checksum
	 */
	public String getChecksum() {
		return checksum;
	}

	/**
	 * @param checksum
	 *            the checksum to set
	 */
	public void setChecksum(final String checksum) {
		this.checksum = checksum;
	}

	/**
	 * @return the ownerName
	 */
	public String getOwnerName() {
		return ownerName;
	}

	/**
	 * @param ownerName
	 *            the ownerName to set
	 */
	public void setOwnerName(final String ownerName) {
		this.ownerName = ownerName;
	}

	/**
	 * @return the ownerZone
	 */
	public String getOwnerZone() {
		return ownerZone;
	}

	/**
	 * @param ownerZone
	 *            the ownerZone to set
	 */
	public void setOwnerZone(final String ownerZone) {
		this.ownerZone = ownerZone;
	}

	/**
	 * @return the objSize
	 */
	public long getObjSize() {
		return objSize;
	}

	/**
	 * @param objSize
	 *            the objSize to set
	 */
	public void setObjSize(final long objSize) {
		this.objSize = objSize;
	}

	/**
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt
	 *            the createdAt to set
	 */
	public void setCreatedAt(final Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the modifiedAt
	 */
	public Date getModifiedAt() {
		return modifiedAt;
	}

	/**
	 * @param modifiedAt
	 *            the modifiedAt to set
	 */
	public void setModifiedAt(final Date modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	/**
	 * @return the specColType
	 */
	public SpecColType getSpecColType() {
		return specColType;
	}

	/**
	 * @param specColType
	 *            the specColType to set
	 */
	public void setSpecColType(final SpecColType specColType) {
		this.specColType = specColType;
	}

	/**
	 * Convenience methods determines if this is any type of collection, versus
	 * a file or data object
	 *
	 * @return <code>boolean</code> of <code>true</code> if this is any type of
	 *         collection or directory;
	 */
	public boolean isSomeTypeOfCollection() {
		return (objectType == ObjectType.COLLECTION || objectType == ObjectType.LOCAL_DIR);
	}

	/**
	 * @return the collectionPath <code>String</code> that indicates that actual
	 *         canonical path to the soft linked collection for soft linked data
	 *         objects
	 */
	public String getCollectionPath() {
		return collectionPath;
	}

	/**
	 * @param collectionPath
	 *            the collectionPath to set <code>String</code> that indicates
	 *            that actual canonical path to the soft linked collection for
	 *            soft linked data objects
	 */
	public void setCollectionPath(final String collectionPath) {
		this.collectionPath = collectionPath;
	}

	/**
	 * @return the cacheDir
	 */
	public String getCacheDir() {
		return cacheDir;
	}

	/**
	 * @param cacheDir
	 *            the cacheDir to set
	 */
	public void setCacheDir(final String cacheDir) {
		this.cacheDir = cacheDir;
	}

	/**
	 * @return the cacheDirty
	 */
	public boolean isCacheDirty() {
		return cacheDirty;
	}

	/**
	 * @param cacheDirty
	 *            the cacheDirty to set
	 */
	public void setCacheDirty(final boolean cacheDirty) {
		this.cacheDirty = cacheDirty;
	}

	/**
	 * @return the replNumber
	 */
	public int getReplNumber() {
		return replNumber;
	}

	/**
	 * @param replNumber
	 *            the replNumber to set
	 */
	public void setReplNumber(final int replNumber) {
		this.replNumber = replNumber;
	}

	/**
	 * @return the objectPath <code>String</code> with either the physical path,
	 *         for a normal file, or the canonical path for a soft linked
	 *         collection
	 */
	public String getObjectPath() {
		return objectPath;
	}

	/**
	 * @param objectPath
	 *            the objectPath to set <code>String</code> with either the
	 *            physical path, for a normal file, or the canonical path for a
	 *            soft linked collection
	 */
	public void setObjectPath(final String objectPath) {
		this.objectPath = objectPath;
	}

	/**
	 * Based on the collection type, determine the absolute path used to query
	 * iRODS. This is meant to handle special collections, such as soft links,
	 * where the iCAT data may be associated with the canonical path
	 *
	 * @param objStat
	 *            {@link ObjStat} with information on the given iRODS object
	 * @return <code>String</code> with the canonical iRODS path
	 */
	public String determineAbsolutePathBasedOnCollTypeInObjectStat() {
		String effectiveAbsolutePath = null;

		if (getSpecColType() == SpecColType.LINKED_COLL) {
			effectiveAbsolutePath = getObjectPath();
		} else {
			effectiveAbsolutePath = getAbsolutePath();
		}
		return effectiveAbsolutePath;
	}

	/**
	 * @return the standInGeneratedObjStat
	 */
	public boolean isStandInGeneratedObjStat() {
		return standInGeneratedObjStat;
	}

	/**
	 * @param standInGeneratedObjStat
	 *            the standInGeneratedObjStat to set
	 */
	public void setStandInGeneratedObjStat(final boolean standInGeneratedObjStat) {
		this.standInGeneratedObjStat = standInGeneratedObjStat;
	}

}
