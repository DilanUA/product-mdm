package org.wso2.carbon.device.mgt.mobile.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.device.mgt.mobile.dao.FeaturePropertyDAO;
import org.wso2.carbon.device.mgt.mobile.dao.MobileDeviceManagementDAOException;
import org.wso2.carbon.device.mgt.mobile.dao.util.MobileDeviceManagementDAOUtil;
import org.wso2.carbon.device.mgt.mobile.dto.FeatureProperty;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of FeaturePropertyDAO
 */
public class FeaturePropertyDAOImpl implements FeaturePropertyDAO {

	private DataSource dataSource;
	private static final Log log = LogFactory.getLog(FeaturePropertyDAOImpl.class);

	public FeaturePropertyDAOImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public boolean addFeatureProperty(FeatureProperty featureProperty)
			throws MobileDeviceManagementDAOException {
		boolean status = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = this.getConnection();
			String createDBQuery =
					"INSERT INTO MBL_FEATURE_PROPERTY(PROPERTY, FEATURE_ID) VALUES (?, ?)";

			stmt = conn.prepareStatement(createDBQuery);
			stmt.setString(1, featureProperty.getProperty());
			stmt.setString(2, featureProperty.getFeatureID());
			int rows = stmt.executeUpdate();
			if (rows > 0) {
				status = true;
			}
		} catch (SQLException e) {
			String msg = "Error occurred while adding property id - '" +
			             featureProperty.getFeatureID() + "'";
			log.error(msg, e);
			throw new MobileDeviceManagementDAOException(msg, e);
		} finally {
			MobileDeviceManagementDAOUtil.cleanupResources(conn, stmt, null);
		}
		return status;
	}

	@Override
	public boolean updateFeatureProperty(FeatureProperty featureProperty)
			throws MobileDeviceManagementDAOException {
		boolean status = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = this.getConnection();
			String updateDBQuery =
					"UPDATE MBL_FEATURE_PROPERTY SET PROPERTY = ?, FEATURE_ID = ? WHERE PROPERTY_ID = ?";
			stmt = conn.prepareStatement(updateDBQuery);
			stmt.setString(1, featureProperty.getProperty());
			stmt.setString(2, featureProperty.getFeatureID());
			stmt.setInt(3, featureProperty.getPropertyId());
			int rows = stmt.executeUpdate();
			if (rows > 0) {
				status = true;
			}
		} catch (SQLException e) {
			String msg = "Error occurred while updating the feature property with property id - '" +
			             featureProperty.getPropertyId() + "'";
			log.error(msg, e);
			throw new MobileDeviceManagementDAOException(msg, e);
		} finally {
			MobileDeviceManagementDAOUtil.cleanupResources(conn, stmt, null);
		}
		return status;
	}

	@Override
	public boolean deleteFeatureProperty(int propertyId)
			throws MobileDeviceManagementDAOException {
		boolean status = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = this.getConnection();
			String deleteDBQuery =
					"DELETE FROM MBL_FEATURE_PROPERTY WHERE PROPERTY_ID = ?";
			stmt = conn.prepareStatement(deleteDBQuery);
			stmt.setInt(1, propertyId);
			int rows = stmt.executeUpdate();
			if (rows > 0) {
				status = true;
			}
		} catch (SQLException e) {
			String msg = "Error occurred while deleting feature property with property Id - " +
			             propertyId;
			log.error(msg, e);
			throw new MobileDeviceManagementDAOException(msg, e);
		} finally {
			MobileDeviceManagementDAOUtil.cleanupResources(conn, stmt, null);
		}
		return status;
	}

	@Override
	public FeatureProperty getFeatureProperty(int propertyId)
			throws MobileDeviceManagementDAOException {
		Connection conn = null;
		PreparedStatement stmt = null;
		FeatureProperty featureProperty = null;
		try {
			conn = this.getConnection();
			String selectDBQuery =
					"SELECT PROPERTY, FEATURE_ID FROM MBL_FEATURE_PROPERTY WHERE PROPERTY_ID = ?";
			stmt = conn.prepareStatement(selectDBQuery);
			stmt.setInt(1, propertyId);
			ResultSet resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				featureProperty = new FeatureProperty();
				featureProperty.setProperty(resultSet.getString(1));
				featureProperty.setFeatureID(resultSet.getString(2));
				break;
			}
		} catch (SQLException e) {
			String msg = "Error occurred while fetching property Id - '" +
			             propertyId + "'";
			log.error(msg, e);
			throw new MobileDeviceManagementDAOException(msg, e);
		} finally {
			MobileDeviceManagementDAOUtil.cleanupResources(conn, stmt, null);
		}
		return featureProperty;
	}

	@Override
	public List<FeatureProperty> getFeaturePropertyOfFeature(String featureId)
			throws MobileDeviceManagementDAOException {
		Connection conn = null;
		PreparedStatement stmt = null;
		FeatureProperty featureProperty = null;
		List<FeatureProperty> FeatureProperties = new ArrayList<FeatureProperty>();
		try {
			conn = this.getConnection();
			String selectDBQuery =
					"SELECT PROPERTY_ID,PROPERTY, FEATURE_ID FROM MBL_FEATURE_PROPERTY WHERE FEATURE_ID = ?";
			stmt = conn.prepareStatement(selectDBQuery);
			stmt.setString(1, featureId);
			ResultSet resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				featureProperty = new FeatureProperty();
				featureProperty.setPropertyId(resultSet.getInt(1));
				featureProperty.setProperty(resultSet.getString(2));
				featureProperty.setFeatureID(resultSet.getString(3));
				FeatureProperties.add(featureProperty);
			}
			return FeatureProperties;
		} catch (SQLException e) {
			String msg = "Error occurred while fetching all feature property.'";
			log.error(msg, e);
			throw new MobileDeviceManagementDAOException(msg, e);
		} finally {
			MobileDeviceManagementDAOUtil.cleanupResources(conn, stmt, null);
		}
	}

	private Connection getConnection() throws MobileDeviceManagementDAOException {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			String msg = "Error occurred while obtaining a connection from the mobile device " +
			             "management metadata repository datasource.";
			log.error(msg, e);
			throw new MobileDeviceManagementDAOException(msg, e);
		}
	}
}