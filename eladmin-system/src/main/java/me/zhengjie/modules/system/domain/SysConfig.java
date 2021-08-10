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
package me.zhengjie.modules.system.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import me.zhengjie.base.BaseEntity;
import me.zhengjie.utils.enums.RegularExpEnum;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author ron
 * @website https://el-admin.vip
 * @description /
 * @date 2021-07-28
 **/
@Entity
@Data
@Table(name = "sys_config")
public class SysConfig extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "config_id")
    @ApiModelProperty(value = "自增主鍵")
    @NotNull(groups = Update.class)
    private Long configId;

    @Column(name = "type", nullable = false)
    @NotBlank
    @Pattern(regexp = RegularExpEnum.Constants.NUM_LETTER_CODE, message = RegularExpEnum.Constants.NUM_LETTER_MESSAGE)
    @ApiModelProperty(value = "類型")
    private String type;

    @Column(name = "value", nullable = false)
    @NotBlank
    @ApiModelProperty(value = "參數值")
    private String value;

    @Column(name = "`desc`", nullable = false)
    @NotBlank
    @ApiModelProperty(value = "描述")
    private String desc;

    @Column(name = "`remark`", nullable = false, columnDefinition = "")
    @ApiModelProperty(value = "備註")
    private String remark;

    public void copy(SysConfig source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }

}