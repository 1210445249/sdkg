package com.fastjrun.packet;

import java.io.Serializable;

/*
 * *
 *  * 注意：本内容仅限于公司内部传阅，禁止外泄以及用于其他的商业目的
 *  *
 *  * @author 崔莹峰
 *  * @Copyright 2018 快嘉框架. All rights reserved.
 *
 */

public class EmptyResponse extends BasePacket<EmptyResponseHead, EmptyResponseBody> implements Serializable {

    private static final long serialVersionUID = 4367248297477303573L;
}