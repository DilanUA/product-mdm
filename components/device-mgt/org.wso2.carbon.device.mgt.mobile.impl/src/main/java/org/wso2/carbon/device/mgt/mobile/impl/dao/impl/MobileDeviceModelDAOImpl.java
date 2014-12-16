/*
 * Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.carbon.device.mgt.mobile.impl.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.device.mgt.mobile.impl.dao.MobileDeviceManagementDAOException;
import org.wso2.carbon.device.mgt.mobile.impl.dao.MobileDeviceModelDAO;
import org.wso2.carbon.device.mgt.mobile.impl.dto.MobileDeviceModel;

import javax.sql.DataSource;

/**
 * Implementation of MobileDeviceModel.
 */
public class MobileDeviceModelDAOImpl implements MobileDeviceModelDAO {

	private DataSource dataSource;
	private static final Log log = LogFactory.getLog(MobileDeviceModelDAOImpl.class);

	public MobileDeviceModelDAOImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public MobileDeviceModel getDeviceModel(String modelId)
			throws MobileDeviceManagementDAOException {
		return null;
	}

	@Override
	public void addDeviceModel(MobileDeviceModel deviceModel)
			throws MobileDeviceManagementDAOException {

	}

	@Override
	public void updateDeviceModel(MobileDeviceModel deviceModel)
			throws MobileDeviceManagementDAOException {

	}

	@Override
	public void deleteDeviceModel(String modelId)
			throws MobileDeviceManagementDAOException {

	}
}