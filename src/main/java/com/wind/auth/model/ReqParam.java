package com.wind.auth.model;

import com.wind.model.BaseObject;


/**
 * UserVO
 *
 * @author qianchun
 * @date 2019/3/18
 **/
public class ReqParam extends BaseObject{

    //---------------------------------------- 基础信息 ----------------------------------------

    /**
     * id
     */
    private Long id;

    /**
     * 名称/姓名
     */
    private String name;

    /**
     * 状态
     */
    private Integer status;

    //---------------------------------------- 分页信息 ----------------------------------------

    /**
     * 当前页
     */
    private Integer pageNo;

    /**
     * 分页大小
     */
    private Integer pageSize;

    /**
     * 总数
     */
    private Integer total;

    /**
     * 分页最后一条数据id
     */
    private Long lastId;

    //---------------------------------------- 用户信息 ----------------------------------------

    /**
     * 证件号
     */
    private String id5;

    /**
     * 证件类型
     */
    private Integer id5Type;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 账号id
     */
    private Integer accountId;

    /**
     * 是否实名认证
     */
    private Integer isAuth;


    //---------------------------------------- 密码信息 ----------------------------------------

    /**
     * 密码
     */
    private String password;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 原密码
     */
    private String oldPwd;

    /**
     * 新密码
     */
    private  String newPwd;
    //---------------------------------------- 地址信息 ----------------------------------------

    /**
     * 省code
     */
    private String provinceCode;
    /**
     * 省名称
     */
    private String provinceName;

    /**
     * 市code
     */
    private String cityId;
    /**
     * 市名称
     */
    private String cityName;

    /**
     * 区县code
     */
    private String countyId;

    /**
     * 区县名称
     */
    private String countyName;

    /**
     * 街道code
     */
    private String streetCode;

    /**
     * 街道名称
     */
    private String streetName;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 邮编
     */
    private String postCode;


    //---------------------------------------- 其他信息 ----------------------------------------
    /**
     * code 码
     */
    private String code;

    /**
     * 备注
     */
    private String remark;


    private ReqParam() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getId5() {
        return id5;
    }

    public void setId5(String id5) {
        this.id5 = id5;
    }

    public Integer getId5Type() {
        return id5Type;
    }

    public void setId5Type(Integer id5Type) {
        this.id5Type = id5Type;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(Integer isAuth) {
        this.isAuth = isAuth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getLastId() {
        return lastId;
    }

    public void setLastId(Long lastId) {
        this.lastId = lastId;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyId() {
        return countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getStreetCode() {
        return streetCode;
    }

    public void setStreetCode(String streetCode) {
        this.streetCode = streetCode;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
