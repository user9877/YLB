package com.bjpowernode.pay.service;

import com.bjpowernode.api.domain.Recharge;
import com.bjpowernode.api.model.UserAccountModel;
import com.bjpowernode.api.result.RPCResult;
import com.bjpowernode.api.service.RechargeService;
import com.bjpowernode.api.service.user.UserService;
import com.bjpowernode.common.Constants;
import com.bjpowernode.common.RedisKeyContants;
import com.bjpowernode.pay.util.Pkipair;
import com.bjpowernode.pay.util.RechargeUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:KQRechargeService
 * Package:com.bjpowernode.pay.service
 * Date:2022/7/27 16:08
 * Description:
 * Autor:FH
 */
@Service
public class KQRechargeService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @DubboReference(interfaceClass = UserService.class,version = "1.0.0")
    private UserService userService;
    @DubboReference(interfaceClass = RechargeService.class,version = "1.0.0")
    private RechargeService rechargeService;
    public Map<String,String> generateFrom(Integer uid, String phone, BigDecimal rechargeMoney){
        Map<String,String> from = new HashMap<>();
        //人民币网关账号，该账号为11位人民币网关商户编号+01,该参数必填。
        String merchantAcctId = "1001214035601";//
        //编码方式，1代表 UTF-8; 2 代表 GBK; 3代表 GB2312 默认为1,该参数必填。
        String inputCharset = "1";
        //接收支付结果的页面地址，该参数一般置为空即可。
        String pageUrl = "";
        //服务器接收支付结果的后台地址，该参数务必填写，不能为空。
        //外网地址才可以
        String bgUrl = "http://localhost:9000/pay/recharge/notify/kq";
        //网关版本，固定值：v2.0,该参数必填。
        String version =  "v2.0";
        //语言种类，1代表中文显示，2代表英文显示。默认为1,该参数必填。
        String language =  "1";
        //签名类型,该值为4，代表PKI加密方式,该参数必填。
        String signType =  "4";
        //支付人姓名,可以为空。
        String payerName= "";
        //支付人联系类型，1 代表电子邮件方式；2 代表手机联系方式。可以为空。
        String payerContactType =  "2";
        //支付人联系方式，与payerContactType设置对应，payerContactType为1，则填写邮箱地址；payerContactType为2，则填写手机号码。可以为空。
        String payerContact =  phone;
        //指定付款人，可以为空
        String payerIdType =  "3";
        //付款人标识，可以为空
        String payerId =  String.valueOf(uid);
        //付款人IP，可以为空
        String payerIP =  "";
        //商家的终端ip，支持Ipv4和Ipv6
        String terminalIp =  "192.168.1.1";
        //网络交易平台简称，英文或中文字符串,除微信支付宝支付外其他交易方式必传
        String tdpformName =  "测试商户-大富科技";
        //商户订单号，以下采用时间来定义订单号，商户可以根据自己订单号的定义规则来定义该值，不能为空。
        String orderId = createOrderId();
        //订单金额，金额以“分”为单位，商户测试以1分测试即可，切勿以大金额测试。该参数必填。
        String orderAmount = rechargeMoney.multiply(new BigDecimal("100")).stripTrailingZeros().toPlainString();
        //订单提交时间，格式：yyyyMMddHHmmss，如：20071117020101，不能为空。
        String orderTime = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        //快钱时间戳，格式：yyyyMMddHHmmss，如：20071117020101， 可以为空
        String orderTimestamp= orderTime;
        //商品名称，可以为空。
        String productName= "充值";
        //商品数量，可以为空。
        String productNum = "1";
        //商品代码，可以为空。
        String productId = "10000";
        //商品描述，不可为空。
        String productDesc = "理财充值";
        //扩展字段1，商户可以传递自己需要的参数，支付完快钱会原值返回，可以为空。
        String ext1 = "";
        //扩展自段2，商户可以传递自己需要的参数，支付完快钱会原值返回，可以为空。
        String ext2 = "";
        //支付方式，一般为00，代表所有的支付方式。如果是银行直连商户，该值为10-1或10-2，必填。
        String payType = "00";
        //银行代码，如果payType为00，该值可以为空；如果payType为10-1或10-2，该值必须填写，具体请参考银行列表。
        String bankId = "";
        //同一订单禁止重复提交标志，实物购物车填1，虚拟产品用0。1代表只能提交一次，0代表在支付不成功情况下可以再提交。可为空。
        String redoFlag = "0";
        //快钱合作伙伴的帐户号，即商户编号，可为空。
        String pid = "";

        // signMsg 签名字符串 不可空，生成加密签名串
        String signMsgVal = "";
        signMsgVal = RechargeUtil.appendParam(signMsgVal, "inputCharset", inputCharset,from);
        signMsgVal = RechargeUtil.appendParam(signMsgVal, "pageUrl", pageUrl,from);
        signMsgVal = RechargeUtil.appendParam(signMsgVal, "bgUrl", bgUrl,from);
        signMsgVal = RechargeUtil.appendParam(signMsgVal, "version", version,from);
        signMsgVal = RechargeUtil.appendParam(signMsgVal, "language", language,from);
        signMsgVal = RechargeUtil.appendParam(signMsgVal, "signType", signType,from);
        signMsgVal = RechargeUtil.appendParam(signMsgVal, "merchantAcctId",merchantAcctId,from);
        signMsgVal = RechargeUtil.appendParam(signMsgVal, "payerName", payerName,from);
        signMsgVal = RechargeUtil.appendParam(signMsgVal, "payerContactType",payerContactType,from);
        signMsgVal = RechargeUtil.appendParam(signMsgVal, "payerContact", payerContact,from);
        signMsgVal = RechargeUtil.appendParam(signMsgVal, "payerIdType", payerIdType,from);
        signMsgVal = RechargeUtil.appendParam(signMsgVal, "payerId", payerId,from);
        signMsgVal = RechargeUtil.appendParam(signMsgVal, "payerIP", payerIP,from);
        signMsgVal = RechargeUtil.appendParam(signMsgVal, "orderId", orderId,from);
        signMsgVal = RechargeUtil.appendParam(signMsgVal, "orderAmount", orderAmount,from);
        signMsgVal = RechargeUtil.appendParam(signMsgVal, "orderTime", orderTime,from);
        signMsgVal = RechargeUtil.appendParam(signMsgVal, "orderTimestamp", orderTimestamp,from);
        signMsgVal = RechargeUtil.appendParam(signMsgVal, "productName", productName,from);
        signMsgVal = RechargeUtil.appendParam(signMsgVal, "productNum", productNum,from);
        signMsgVal = RechargeUtil.appendParam(signMsgVal, "productId", productId,from);
        signMsgVal = RechargeUtil.appendParam(signMsgVal, "productDesc", productDesc,from);
        signMsgVal = RechargeUtil.appendParam(signMsgVal, "ext1", ext1,from);
        signMsgVal = RechargeUtil.appendParam(signMsgVal, "ext2", ext2,from);
        signMsgVal = RechargeUtil.appendParam(signMsgVal, "payType", payType,from);
        signMsgVal = RechargeUtil.appendParam(signMsgVal, "bankId", bankId,from);
        signMsgVal = RechargeUtil.appendParam(signMsgVal, "redoFlag", redoFlag,from);
        signMsgVal = RechargeUtil.appendParam(signMsgVal, "pid", pid,from);
        System.out.println(signMsgVal);
        Pkipair pki = new Pkipair();
        //生成签名串
        String signMsg = pki.signMsg(signMsgVal);
        from.put("signMsg",signMsg);
        from.put("terminalIp",terminalIp);
        from.put("tdpformName",tdpformName);
        return from;
    }
    //异步通知
    public void doNotify(HttpServletRequest request){
        String merchantAcctId = request.getParameter("merchantAcctId");
        String version = request.getParameter("version");
        String language = request.getParameter("language");
        String signType = request.getParameter("signType");
        String payType = request.getParameter("payType");
        String bankId = request.getParameter("bankId");
        String orderId = request.getParameter("orderId");
        String orderTime = request.getParameter("orderTime");
        String dealId = request.getParameter("dealId");
        String orderAmount = request.getParameter("orderAmount");
        String bindCard = request.getParameter("bindCard");
        if(request.getParameter("bindCard")!=null){
            bindCard = request.getParameter("bindCard");}
        String bindMobile="";
        if(request.getParameter("bindMobile")!=null){
            bindMobile = request.getParameter("bindMobile");}
        String bankDealId = request.getParameter("bankDealId");
        String dealTime = request.getParameter("dealTime");
        String payAmount = request.getParameter("payAmount");
        String fee = request.getParameter("fee");
        String ext1 = request.getParameter("ext1");
        String ext2 = request.getParameter("ext2");
        String payResult = request.getParameter("payResult");
        String aggregatePay = request.getParameter("aggregatePay");
        String errCode = request.getParameter("errCode");
        String signMsg = request.getParameter("signMsg");
        String merchantSignMsgVal = "";
        merchantSignMsgVal = RechargeUtil.appendParam(merchantSignMsgVal,"merchantAcctId", merchantAcctId);
        merchantSignMsgVal = RechargeUtil.appendParam(merchantSignMsgVal, "version",version);
        merchantSignMsgVal = RechargeUtil.appendParam(merchantSignMsgVal, "language",language);
        merchantSignMsgVal = RechargeUtil.appendParam(merchantSignMsgVal, "signType",signType);
        merchantSignMsgVal = RechargeUtil.appendParam(merchantSignMsgVal, "payType",payType);
        merchantSignMsgVal = RechargeUtil.appendParam(merchantSignMsgVal, "bankId",bankId);
        merchantSignMsgVal = RechargeUtil.appendParam(merchantSignMsgVal, "orderId",orderId);
        merchantSignMsgVal = RechargeUtil.appendParam(merchantSignMsgVal, "orderTime",orderTime);
        merchantSignMsgVal = RechargeUtil.appendParam(merchantSignMsgVal, "orderAmount",orderAmount);
        merchantSignMsgVal = RechargeUtil.appendParam(merchantSignMsgVal, "bindCard",bindCard);
        merchantSignMsgVal = RechargeUtil.appendParam(merchantSignMsgVal, "bindMobile",bindMobile);
        merchantSignMsgVal = RechargeUtil.appendParam(merchantSignMsgVal, "dealId",dealId);
        merchantSignMsgVal = RechargeUtil.appendParam(merchantSignMsgVal, "bankDealId",bankDealId);
        merchantSignMsgVal = RechargeUtil.appendParam(merchantSignMsgVal, "dealTime",dealTime);
        merchantSignMsgVal = RechargeUtil.appendParam(merchantSignMsgVal, "payAmount",payAmount);
        merchantSignMsgVal = RechargeUtil.appendParam(merchantSignMsgVal, "fee", fee);
        merchantSignMsgVal = RechargeUtil.appendParam(merchantSignMsgVal, "ext1", ext1);
        merchantSignMsgVal = RechargeUtil.appendParam(merchantSignMsgVal, "ext2", ext2);
        merchantSignMsgVal = RechargeUtil.appendParam(merchantSignMsgVal, "payResult",payResult);
        merchantSignMsgVal = RechargeUtil.appendParam(merchantSignMsgVal, "aggregatePay",aggregatePay);
        merchantSignMsgVal = RechargeUtil.appendParam(merchantSignMsgVal, "errCode",errCode);
        Pkipair pki = new Pkipair();
        boolean flag = pki.enCodeByCer(merchantSignMsgVal, signMsg);
        flag = true;
        if(flag){
            //验证商家号
            if("1001214035601".equals(merchantAcctId)){
                RPCResult result = rechargeService.handleRechargeNofity(orderId,payResult,payAmount);
                System.out.println("支付的处理结果："+result.getText());
            }else{
                System.out.println("订单："+orderId+"不是商家的订单,不能处理,商户号是："+merchantAcctId);
            }
        }else{
            System.out.println("订单："+orderId+"验签失败,不能处理");
        }
        //从redis删除处理过的订单
        stringRedisTemplate.boundZSetOps(RedisKeyContants.RECHARGE_ORDER_ID).remove(orderId);
    }
    private synchronized String createOrderId(){
        String date = "KQ"+new java.text.SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date());
        Long seq = stringRedisTemplate.boundValueOps(RedisKeyContants.RECHARGE_KQ_SEQ).increment();
        return date + seq;
    }

    public UserAccountModel queryUserByUid(Integer uid){
        UserAccountModel user = userService.queryAllInfoByUid(uid);
        return user;
    }

    public boolean addRecharge(Integer uid,String orderId,BigDecimal rechargeMoney,String channel) {
        Recharge recharge = new Recharge();
        recharge.setUid(uid);
        recharge.setRechargeNo(orderId);
        recharge.setRechargeDesc("理财充值");
        recharge.setRechargeMoney(rechargeMoney);
        recharge.setRechargeTime(new Date());
        recharge.setRechargeStatus(Constants.RECHARGE_STATUS_0);
        recharge.setChannel(channel);
        return rechargeService.addRecharge(recharge);
    }

    public void addOrderIdToRedis(String orderId) {
        stringRedisTemplate.boundZSetOps(RedisKeyContants.RECHARGE_ORDER_ID).add(orderId,new Date().getTime());
    }
}
