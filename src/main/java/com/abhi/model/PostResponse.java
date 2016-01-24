package com.abhi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostResponse extends BaseResponse
{

}
