/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.modules.system.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;
import java.util.List;

/**
* @website https://el-admin.vip
* @description /
* @author ron
* @date 2021-07-28
**/
@Data
public class SysConfigDto implements Serializable {

    /** 自增主鍵 */
    private Long configId;

    /** 類型 */
    private String type;

    private List<DictDetailDto> dictDetails;

    /** 參數值 */
    private String value;

    /** 描述 */
    private String desc;

    /** 備註 */
    private String remark;

    /** 創建人 */
    private String createBy;

    /** 更新人 */
    private String updateBy;

    /** 創建時間 */
    private Timestamp createTime;

    /** 更新時間 */
    private Timestamp updateTime;

}