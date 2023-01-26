package com.rolixtech.cravings.module.order.services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;

import com.rolixtech.cravings.CravingsApplication;
import com.rolixtech.cravings.module.generic.services.GenericUtility;
import com.rolixtech.cravings.module.order.POJO.OrderAddressPOJO;
import com.rolixtech.cravings.module.order.POJO.OrderPOJO;
import com.rolixtech.cravings.module.order.POJO.OrderProductsOptionalAddOnPOJO;
import com.rolixtech.cravings.module.order.POJO.OrderProductsPOJO;
import com.rolixtech.cravings.module.order.POJO.OrderProductsRequiredAddOnPOJO;
import com.rolixtech.cravings.module.order.dao.CustomerOrderAddressDao;
import com.rolixtech.cravings.module.order.dao.CustomerOrderDao;
import com.rolixtech.cravings.module.order.dao.CustomerOrderProductsDao;
import com.rolixtech.cravings.module.order.dao.CustomerOrderProductsOptionalAddOnDao;
import com.rolixtech.cravings.module.order.dao.CustomerOrderProductsRequiredAddOnDao;
import com.rolixtech.cravings.module.order.model.CustomerOrder;
import com.rolixtech.cravings.module.order.model.CustomerOrderAddress;
import com.rolixtech.cravings.module.order.model.CustomerOrderProducts;
import com.rolixtech.cravings.module.order.model.CustomerOrderProductsOptionalAddOn;
import com.rolixtech.cravings.module.order.model.CustomerOrderProductsRequiredAddOn;
import com.rolixtech.cravings.module.resturant.dao.CommonCategoriesDao;
import com.rolixtech.cravings.module.resturant.dao.CommonResturantsProductsDao;
import com.rolixtech.cravings.module.resturant.model.CommonCategories;
import com.rolixtech.cravings.module.resturant.model.CommonResturants;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsCategories;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsProducts;
import com.rolixtech.cravings.module.resturant.services.CommonResturantsCategoriesService;
import com.rolixtech.cravings.module.resturant.services.CommonResturantsProductsAddOnService;
import com.rolixtech.cravings.module.resturant.services.CommonResturantsProductsService;
import com.rolixtech.cravings.module.resturant.services.CommonResturantsService;

@Service
public class CustomerOrdersService {
	
	@Autowired
	private CustomerOrderDao OrderDao;
	
	@Autowired
	private CustomerOrderAddressDao OrderAddressDao;
	
	@Autowired
	private CustomerOrderProductsDao OrderProductsDao;
	
	@Autowired
	private CustomerOrderProductsOptionalAddOnDao OrderProductsOptionalAddOnDao;
	@Autowired
	private CustomerOrderProductsRequiredAddOnDao OrderProductsRequiredAddOnDao;
	
	@Autowired
	private CommonResturantsService ResturantsService;
	
	@Autowired
	private CommonResturantsProductsService ResturantsProductsService;
	
	@Autowired
	private CustomerOrderStatusService OrderStatusService;
	
	@Autowired
	private CommonResturantsProductsDao ResturantsProductsDao;	
	
	@Autowired
	private CommonResturantsCategoriesService ResturantsCategoriesService;
	
	@Autowired
	private CommonResturantsProductsAddOnService ProductsAddOnService;
	
	@Autowired
	private GenericUtility utility;
	
	public String findLabelById(long categoryId) {
		String label="";
//		CommonCategories Categories=CategoriesDao.findById(categoryId);
//		if(Categories!=null) {
//			label=Categories.getLabel();
//		}
		return label;
	}
	
	public void Save(OrderPOJO order){
		
		OrderPOJO orderr=order;
		if(orderr!=null)
		{ 
			long resturantId=orderr.getResturantId();
			double totalAmount=orderr.getTotalAmount();
			double totalGst=orderr.getTotalGst();
			String orderType=orderr.getOrderType();
			long userId=orderr.getUserId();
			double subtotal=orderr.getSubtotal();
			double discount=orderr.getDiscount();
			double deliveryFee=orderr.getDeliveryFee();
			
			CustomerOrder Order=new CustomerOrder();
			Order.setResturantId(resturantId);
			Order.setOrderType(orderType);
			Order.setOrderNumber(orderType);
			Order.setTotalAmount(totalAmount);
			Order.setTotalGst(totalGst);
			Order.setDeliveryFee(deliveryFee);
			Order.setDiscount(discount);
			Order.setSubtotal(subtotal);
			Order.setUserId(userId);
			Order.setCreatedOn(new Date());
			Order.setOrderStatusId(1);
			OrderDao.save(Order);
			
			OrderAddressPOJO address=orderr.getAddress();
			if(address!=null) {
				CustomerOrderAddress Address=new CustomerOrderAddress();
				Address.setCityName(address.getCityName());
				Address.setCompleteAddress(address.getCompleteAddress());
				Address.setLat(address.getLat());
				Address.setLng(address.getLng());
				Address.setOrderId(Order.getId());
				OrderAddressDao.save(Address);
				
			}
			
			List<OrderProductsPOJO> Products=orderr.getProducts();
			if(!Products.isEmpty()) {
				Products.stream().forEach(
					product->{
						
						OrderProductsPOJO singleProduct=product;
						if(singleProduct!=null) {
							double price=singleProduct.getPrice();
							int quantity=singleProduct.getQuantity();
							long productId=singleProduct.getProductId();
							
							
							CustomerOrderProducts OrderProducts=new CustomerOrderProducts();
							OrderProducts.setPrice(price);
							OrderProducts.setProductId(productId);
							OrderProducts.setQuantity(quantity);
							OrderProducts.setOrderId(Order.getId());
							OrderProductsDao.save(OrderProducts);
							
							
							List<OrderProductsRequiredAddOnPOJO> requiredAddOns= singleProduct.getRequiredAddOn();
							if(!requiredAddOns.isEmpty()) {
								requiredAddOns.stream().forEach(
										requiredAddOn->{
											
											OrderProductsRequiredAddOnPOJO addOn=requiredAddOn;
											if(addOn!=null) {
												long addOnproductId=addOn.getId();
												double addOnPrice=addOn.getPrice();
												
												OrderProductsRequiredAddOnDao.save(new CustomerOrderProductsRequiredAddOn(OrderProducts.getId(),addOnproductId,addOnPrice));
											}
									}
								);
							}	
							
							List<OrderProductsOptionalAddOnPOJO> optionalAddOns= singleProduct.getOptionalAddOn();
							if(!optionalAddOns.isEmpty()) {
								optionalAddOns.stream().forEach(
										optionalAddOn->{
											OrderProductsOptionalAddOnPOJO addOn=optionalAddOn;
											if(addOn!=null) {
												long addOnproductId=addOn.getId();
												double addOnPrice=addOn.getPrice();
												OrderProductsOptionalAddOnDao.save(new CustomerOrderProductsOptionalAddOn(OrderProducts.getId(),addOnproductId,addOnPrice));
												
											}
									}
								);
							}
						}
						
						
						
					}
				);
			}
			
		}
		
	}
	
	
	public List<Map> getAllUserPastOrdersSummary(long orderIdd){
		
		List<Map> list=new ArrayList<>();
		List<CustomerOrder> orders=OrderDao.findAllById(orderIdd);
		if(!orders.isEmpty()) {
			orders.stream().forEach(
				order->{
					
						long orderId=order.getId();
						Map Row=new HashMap<>();
						Row.put("id", order.getId());
						Row.put("resturantId", order.getResturantId());
						Row.put("resturantLabel",ResturantsService.findLabelById(order.getResturantId()));
						Row.put("resturantBanner",ResturantsService.findBannerImgById(order.getResturantId()));
						Row.put("orderNumber", order.getOrderNumber());
						Row.put("totalAmount", order.getTotalAmount());
						Row.put("totalGst", order.getTotalGst());
						Row.put("orderType", order.getOrderType());
						Row.put("createdOn", order.getCreatedOn());
						Row.put("orderStatusId", order.getOrderStatusId());
						Row.put("orderStatusLabel", OrderStatusService.findLabelById(order.getOrderStatusId()));
						Row.put("subtotal", order.getSubtotal());
						Row.put("discount", order.getDiscount());
						Row.put("deliveryFee", order.getDeliveryFee());
						
						CustomerOrderAddress Address=OrderAddressDao.findByOrderId(orderId);
						if(Address!=null) {	
							Row.put("cityName", Address.getCityName());
							Row.put("completeAddress", Address.getCompleteAddress());
							
						}else {
							Row.put("cityName", "");
							Row.put("completeAddress", "");
							
						}
						
						List<Map> orderProductsList=new ArrayList<>();
						List<CustomerOrderProducts> OrderProducts=OrderProductsDao.findAllByOrderId(orderId);
						if(!OrderProducts.isEmpty()) {
							OrderProducts.stream().forEach(
								OrderProduct->{
									Map Rowproduct=new HashMap<>();
									Rowproduct.put("price", OrderProduct.getPrice());
									Rowproduct.put("productId", OrderProduct.getProductId());
									Rowproduct.put("productLabel",ResturantsProductsService.findLabelById(OrderProduct.getProductId()));
									Rowproduct.put("quantity", OrderProduct.getQuantity());
									
									
									List<Map> requiredAddOnsList=new ArrayList<>();
									List<CustomerOrderProductsRequiredAddOn> requiredAddOns=OrderProductsRequiredAddOnDao.findAllByOrderProductId(OrderProduct.getId());
									if(!requiredAddOns.isEmpty()) {
										requiredAddOns.stream().forEach(
												requiredAddOn->{
													Map RowproductRequiredAddon=new HashMap<>();
													RowproductRequiredAddon.put("price", requiredAddOn.getPrice());
													RowproductRequiredAddon.put("id", requiredAddOn.getProductId());
													RowproductRequiredAddon.put("label",ResturantsProductsService.findLabelById(OrderProduct.getProductId()));
													requiredAddOnsList.add(RowproductRequiredAddon);
													
													
												}
										);
									}
									Rowproduct.put("requiredAddOn", requiredAddOnsList);
									
									List<Map> optionalAddOnsList=new ArrayList<>();
									List<CustomerOrderProductsOptionalAddOn> optionalAddOns=OrderProductsOptionalAddOnDao.findAllByOrderProductId(OrderProduct.getId());
									if(!optionalAddOns.isEmpty()) {
										optionalAddOns.stream().forEach(
												optionalAddOn->{
													Map RowproductOptionalAddon=new HashMap<>();
													RowproductOptionalAddon.put("price", optionalAddOn.getPrice());
													RowproductOptionalAddon.put("id", optionalAddOn.getProductId());
													RowproductOptionalAddon.put("label",ResturantsProductsService.findLabelById(optionalAddOn.getProductId()));
													optionalAddOnsList.add(RowproductOptionalAddon);
													
													
												}
										);
									}
									Rowproduct.put("optionalAddOn", optionalAddOnsList);
									
									orderProductsList.add(Rowproduct);
										
								}
							);
						}
						
						Row.put("products",orderProductsList);
						
						list.add(Row);
				}
			
			);
		}
		
		
		
		return list;
		
	}
	
	public List<Map> getAllUserPastOrders(long userId){
		
		List<Map> list=new ArrayList<>();
		List<CustomerOrder> orders=OrderDao.findAllByUserId(userId);
		if(!orders.isEmpty()) {
			orders.stream().forEach(
				order->{
					
						long orderId=order.getId();
						Map Row=new HashMap<>();
						Row.put("id", order.getId());
						Row.put("resturantId", order.getResturantId());
						Row.put("resturantLabel",ResturantsService.findLabelById(order.getResturantId()));
						Row.put("resturantLogo",ResturantsService.findLogoImgById(order.getResturantId()));
						Row.put("totalAmount", order.getTotalAmount());
						Row.put("createdOn", order.getCreatedOn());
						Row.put("orderstatus", order.getOrderStatusId());
						Row.put("orderStatusId", order.getOrderStatusId());
						Row.put("orderStatusLabel", OrderStatusService.findLabelById(order.getOrderStatusId()));
						Row.put("subtotal", order.getSubtotal());
						Row.put("discount", order.getDiscount());
						Row.put("deliveryFee", order.getDeliveryFee());
						String products = "";
						List<CustomerOrderProducts> OrderProducts=OrderProductsDao.findAllByOrderId(orderId);
						if(!OrderProducts.isEmpty()) {
							 
						    Map Rowproduct=new HashMap<>();
							for(int p=0;p<OrderProducts.size();p++) {	
									
								if(products.equals("")) {
									products=ResturantsProductsService.findLabelById(OrderProducts.get(p).getProductId());
								}else {
									products+=","+ResturantsProductsService.findLabelById(OrderProducts.get(p).getProductId());
								}	
							}
							
						}
						
						Row.put("products",products);
						
						list.add(Row);
				}
			
			);
		}
		
		
		
		return list;
		
	}
	
	
	public List<Map> getUserActiveOrder(long userId){
		
		List<Map> list=new ArrayList<>();
		List<CustomerOrder> orders=OrderDao.findAllByUserIdAndOrderStatusId(userId,2l);
		if(!orders.isEmpty()) {
			orders.stream().forEach(
				order->{
					
						long orderId=order.getId();
						Map Row=new HashMap<>();
						Row.put("id", order.getId());
						Row.put("resturantId", order.getResturantId());
						Row.put("resturantLabel",ResturantsService.findLabelById(order.getResturantId()));
						Row.put("resturantLogo",ResturantsService.findLogoImgById(order.getResturantId()));
						Row.put("totalAmount", order.getTotalAmount());
						Row.put("createdOn", order.getCreatedOn());
						Row.put("orderStatusId", order.getOrderStatusId());
						Row.put("orderStatusLabel", OrderStatusService.findLabelById(order.getOrderStatusId()));
						Row.put("subtotal", order.getSubtotal());
						Row.put("discount", order.getDiscount());
						Row.put("deliveryFee", order.getDeliveryFee());
						
						String products = "";
						List<CustomerOrderProducts> OrderProducts=OrderProductsDao.findAllByOrderId(orderId);
						if(!OrderProducts.isEmpty()) {
							 
						    Map Rowproduct=new HashMap<>();
							for(int p=0;p<OrderProducts.size();p++) {	
									
								if(products.equals("")) {
									products=ResturantsProductsService.findLabelById(OrderProducts.get(p).getProductId());
								}else {
									products+=","+ResturantsProductsService.findLabelById(OrderProducts.get(p).getProductId());
								}	
							}
							
						}
						
						Row.put("products",products);
						
						list.add(Row);
				}
			
			);
		}
		
		
		
		return list;
		
	}

	
	public List<Map> getUserActiveOrdersSummary(long orderIdd){
		
		List<Map> list=new ArrayList<>();
		CustomerOrder order=OrderDao.findByUserIdAndOrderStatusId(orderIdd,2l);
		if(order!=null) {
					
			long orderId=order.getId();
			Map Row=new HashMap<>();
			Row.put("id", order.getId());
			Row.put("resturantId", order.getResturantId());
			Row.put("resturantLabel",ResturantsService.findLabelById(order.getResturantId()));
			Row.put("resturantBanner",ResturantsService.findBannerImgById(order.getResturantId()));
			Row.put("orderNumber", order.getOrderNumber());
			Row.put("totalAmount", order.getTotalAmount());
			Row.put("totalGst", order.getTotalGst());
			Row.put("orderType", order.getOrderType());
			Row.put("createdOn", order.getCreatedOn());
			Row.put("orderStatusId", order.getOrderStatusId());
			Row.put("orderStatusLabel", OrderStatusService.findLabelById(order.getOrderStatusId()));
			Row.put("subtotal", order.getSubtotal());
			Row.put("discount", order.getDiscount());
			Row.put("deliveryFee", order.getDeliveryFee());
			
			CustomerOrderAddress Address=OrderAddressDao.findByOrderId(orderId);
			if(Address!=null) {	
				Row.put("cityName", Address.getCityName());
				Row.put("completeAddress", Address.getCompleteAddress());
				
			}else {
				Row.put("cityName", "");
				Row.put("completeAddress", "");
				
			}
			
			List<Map> orderProductsList=new ArrayList<>();
			List<CustomerOrderProducts> OrderProducts=OrderProductsDao.findAllByOrderId(orderId);
			if(!OrderProducts.isEmpty()) {
				OrderProducts.stream().forEach(
					OrderProduct->{
						Map Rowproduct=new HashMap<>();
						Rowproduct.put("price", OrderProduct.getPrice());
						Rowproduct.put("productId", OrderProduct.getProductId());
						Rowproduct.put("productLabel",ResturantsProductsService.findLabelById(OrderProduct.getProductId()));
						Rowproduct.put("quantity", OrderProduct.getQuantity());
						
						
						List<Map> requiredAddOnsList=new ArrayList<>();
						List<CustomerOrderProductsRequiredAddOn> requiredAddOns=OrderProductsRequiredAddOnDao.findAllByOrderProductId(OrderProduct.getId());
						if(!requiredAddOns.isEmpty()) {
							requiredAddOns.stream().forEach(
									requiredAddOn->{
										Map RowproductRequiredAddon=new HashMap<>();
										RowproductRequiredAddon.put("price", requiredAddOn.getPrice());
										RowproductRequiredAddon.put("id", requiredAddOn.getProductId());
										RowproductRequiredAddon.put("label",ResturantsProductsService.findLabelById(requiredAddOn.getProductId()));
										requiredAddOnsList.add(RowproductRequiredAddon);
										
										
									}
							);
						}
						Rowproduct.put("requiredAddOn", requiredAddOnsList);
						
						List<Map> optionalAddOnsList=new ArrayList<>();
						List<CustomerOrderProductsOptionalAddOn> optionalAddOns=OrderProductsOptionalAddOnDao.findAllByOrderProductId(OrderProduct.getId());
						if(!optionalAddOns.isEmpty()) {
							optionalAddOns.stream().forEach(
									optionalAddOn->{
										Map RowproductOptionalAddon=new HashMap<>();
										RowproductOptionalAddon.put("price", optionalAddOn.getPrice());
										RowproductOptionalAddon.put("id", optionalAddOn.getProductId());
										RowproductOptionalAddon.put("label",ResturantsProductsService.findLabelById(optionalAddOn.getProductId()));
										optionalAddOnsList.add(RowproductOptionalAddon);
										
										
									}
							);
						}
						Rowproduct.put("optionalAddOn", optionalAddOnsList);
						
						orderProductsList.add(Rowproduct);
							
					}
				);
			}
			
			Row.put("products",orderProductsList);
			
			list.add(Row);
				
		}
		
		
		
		return list;
		
	}
	
	

	public List<Map> getAllUserActiveOrdersSummary(long orderIdd){
		
		List<Map> list=new ArrayList<>();
		List<CustomerOrder> orders=OrderDao.findAllById(orderIdd);
		if(!orders.isEmpty()) {
			orders.stream().forEach(
				order->{
					
						long orderId=order.getId();
						Map Row=new HashMap<>();
						Row.put("id", order.getId());
						Row.put("resturantId", order.getResturantId());
						Row.put("resturantLabel",ResturantsService.findLabelById(order.getResturantId()));
						Row.put("resturantBanner",ResturantsService.findBannerImgById(order.getResturantId()));
						Row.put("orderNumber", order.getOrderNumber());
						Row.put("totalAmount", order.getTotalAmount());
						Row.put("totalGst", order.getTotalGst());
						Row.put("orderType", order.getOrderType());
						Row.put("createdOn", order.getCreatedOn());
						Row.put("orderStatusId", order.getOrderStatusId());
						Row.put("orderStatusLabel", OrderStatusService.findLabelById(order.getOrderStatusId()));
						Row.put("subtotal", order.getSubtotal());
						Row.put("discount", order.getDiscount());
						Row.put("deliveryFee", order.getDeliveryFee());
						CustomerOrderAddress Address=OrderAddressDao.findByOrderId(orderId);
						if(Address!=null) {	
							Row.put("cityName", Address.getCityName());
							Row.put("completeAddress", Address.getCompleteAddress());
							
						}else {
							Row.put("cityName", "");
							Row.put("completeAddress", "");
							
						}
						
						List<Map> orderProductsList=new ArrayList<>();
						List<CustomerOrderProducts> OrderProducts=OrderProductsDao.findAllByOrderId(orderId);
						if(!OrderProducts.isEmpty()) {
							OrderProducts.stream().forEach(
								OrderProduct->{
									Map Rowproduct=new HashMap<>();
									Rowproduct.put("price", OrderProduct.getPrice());
									Rowproduct.put("productId", OrderProduct.getProductId());
									Rowproduct.put("quantity", OrderProduct.getQuantity());
									Rowproduct.put("productLabel",ResturantsProductsService.findLabelById(OrderProduct.getProductId()));
									
									List<Map> requiredAddOnsList=new ArrayList<>();
									List<CustomerOrderProductsRequiredAddOn> requiredAddOns=OrderProductsRequiredAddOnDao.findAllByOrderProductId(OrderProduct.getId());
									if(!requiredAddOns.isEmpty()) {
										requiredAddOns.stream().forEach(
												requiredAddOn->{
													Map RowproductRequiredAddon=new HashMap<>();
													RowproductRequiredAddon.put("price", requiredAddOn.getPrice());
													RowproductRequiredAddon.put("id", requiredAddOn.getProductId());
													RowproductRequiredAddon.put("label",ResturantsProductsService.findLabelById(requiredAddOn.getProductId()));
													requiredAddOnsList.add(RowproductRequiredAddon);
													
													
												}
										);
									}
									Rowproduct.put("requiredAddOn", requiredAddOnsList);
									
									List<Map> optionalAddOnsList=new ArrayList<>();
									List<CustomerOrderProductsOptionalAddOn> optionalAddOns=OrderProductsOptionalAddOnDao.findAllByOrderProductId(OrderProduct.getId());
									if(!optionalAddOns.isEmpty()) {
										optionalAddOns.stream().forEach(
												optionalAddOn->{
													Map RowproductOptionalAddon=new HashMap<>();
													RowproductOptionalAddon.put("price", optionalAddOn.getPrice());
													RowproductOptionalAddon.put("id", optionalAddOn.getProductId());
													RowproductOptionalAddon.put("label",ResturantsProductsService.findLabelById(optionalAddOn.getProductId()));
													optionalAddOnsList.add(RowproductOptionalAddon);
													
													
												}
										);
									}
									Rowproduct.put("optionalAddOn", optionalAddOnsList);
									
									orderProductsList.add(Rowproduct);
										
								}
							);
						}
						
						Row.put("products",orderProductsList);
						
						list.add(Row);
				}
			
			);
		}
		
		
		
		return list;
		
	}

	public List<Map> getAllActiveOrders() {
		List<Map> list=new ArrayList<>();
		
		List<CustomerOrder> orders=OrderDao.findAllByOrderStatusIdGreaterThan(1l);
		if(!orders.isEmpty()) {
			orders.stream().forEach(
				order->{
					
						long orderId=order.getId();
						Map Row=new HashMap<>();
						Row.put("id", order.getId());
						Row.put("resturantId", order.getResturantId());
						Row.put("resturantLabel",ResturantsService.findLabelById(order.getResturantId()));
						Row.put("resturantBanner",ResturantsService.findBannerImgById(order.getResturantId()));
						Row.put("orderNumber", order.getOrderNumber());
						Row.put("totalAmount", order.getTotalAmount());
						Row.put("totalGst", order.getTotalGst());
						Row.put("orderType", order.getOrderType());
						Row.put("createdOn", order.getCreatedOn());
						Row.put("orderStatusId", order.getOrderStatusId());
						Row.put("orderStatusLabel", OrderStatusService.findLabelById(order.getOrderStatusId()));
						Row.put("subtotal", order.getSubtotal());
						Row.put("discount", order.getDiscount());
						Row.put("deliveryFee", order.getDeliveryFee());
						CustomerOrderAddress Address=OrderAddressDao.findByOrderId(orderId);
						if(Address!=null) {	
							Row.put("cityName", Address.getCityName());
							Row.put("completeAddress", Address.getCompleteAddress());
							
						}else {
							Row.put("cityName", "");
							Row.put("completeAddress", "");
							
						}
						
						List<Map> orderProductsList=new ArrayList<>();
						List<CustomerOrderProducts> OrderProducts=OrderProductsDao.findAllByOrderId(orderId);
						if(!OrderProducts.isEmpty()) {
							OrderProducts.stream().forEach(
								OrderProduct->{
									Map Rowproduct=new HashMap<>();
									Rowproduct.put("price", OrderProduct.getPrice());
									Rowproduct.put("productId", OrderProduct.getProductId());
									Rowproduct.put("quantity", OrderProduct.getQuantity());
									Rowproduct.put("productLabel",ResturantsProductsService.findLabelById(OrderProduct.getProductId()));
									
									List<Map> requiredAddOnsList=new ArrayList<>();
									List<CustomerOrderProductsRequiredAddOn> requiredAddOns=OrderProductsRequiredAddOnDao.findAllByOrderProductId(OrderProduct.getId());
									if(!requiredAddOns.isEmpty()) {
										requiredAddOns.stream().forEach(
												requiredAddOn->{
													Map RowproductRequiredAddon=new HashMap<>();
													RowproductRequiredAddon.put("price", requiredAddOn.getPrice());
													RowproductRequiredAddon.put("id", requiredAddOn.getProductId());
													RowproductRequiredAddon.put("label",ResturantsProductsService.findLabelById(requiredAddOn.getProductId()));
													requiredAddOnsList.add(RowproductRequiredAddon);
													
													
												}
										);
									}
									Rowproduct.put("requiredAddOn", requiredAddOnsList);
									
									List<Map> optionalAddOnsList=new ArrayList<>();
									List<CustomerOrderProductsOptionalAddOn> optionalAddOns=OrderProductsOptionalAddOnDao.findAllByOrderProductId(OrderProduct.getId());
									if(!optionalAddOns.isEmpty()) {
										optionalAddOns.stream().forEach(
												optionalAddOn->{
													Map RowproductOptionalAddon=new HashMap<>();
													RowproductOptionalAddon.put("price", optionalAddOn.getPrice());
													RowproductOptionalAddon.put("id", optionalAddOn.getProductId());
													RowproductOptionalAddon.put("label",ResturantsProductsService.findLabelById(optionalAddOn.getProductId()));
													optionalAddOnsList.add(RowproductOptionalAddon);
													
													
												}
										);
									}
									Rowproduct.put("optionalAddOn", optionalAddOnsList);
									
									orderProductsList.add(Rowproduct);
										
								}
							);
						}
						
						Row.put("products",orderProductsList);
						
						list.add(Row);
				}
			
			);
		}
		
		
		
		return list;
	}
	
	public List<Map> getActiveOrdersByOrderIdAdmin(long orderId) {
		List<Map> list=new ArrayList<>();
		
		CustomerOrder order=OrderDao.findAllByIdAndOrderStatusIdGreaterThan(orderId,1l);
		if(order!=null) {
			
					
					
				Map Row=new HashMap<>();
				Row.put("id", order.getId());
				Row.put("resturantId", order.getResturantId());
				Row.put("resturantLabel",ResturantsService.findLabelById(order.getResturantId()));
				Row.put("resturantBanner",ResturantsService.findBannerImgById(order.getResturantId()));
				Row.put("orderNumber", order.getOrderNumber());
				Row.put("totalAmount", order.getTotalAmount());
				Row.put("totalGst", order.getTotalGst());
				Row.put("orderType", order.getOrderType());
				Row.put("createdOn", order.getCreatedOn());
				Row.put("orderStatusId", order.getOrderStatusId());
				Row.put("orderStatusLabel", OrderStatusService.findLabelById(order.getOrderStatusId()));
				Row.put("subtotal", order.getSubtotal());
				Row.put("discount", order.getDiscount());
				Row.put("deliveryFee", order.getDeliveryFee());
				CustomerOrderAddress Address=OrderAddressDao.findByOrderId(orderId);
				if(Address!=null) {	
					Row.put("cityName", Address.getCityName());
					Row.put("completeAddress", Address.getCompleteAddress());
					
				}else {
					Row.put("cityName", "");
					Row.put("completeAddress", "");
					
				}
				
				List<Map> orderProductsList=new ArrayList<>();
				List<CustomerOrderProducts> OrderProducts=OrderProductsDao.findAllByOrderId(orderId);
				if(!OrderProducts.isEmpty()) {
					OrderProducts.stream().forEach(
						OrderProduct->{
							Map Rowproduct=new HashMap<>();
							Rowproduct.put("price", OrderProduct.getPrice());
							Rowproduct.put("productId", OrderProduct.getProductId());
							Rowproduct.put("quantity", OrderProduct.getQuantity());
							Rowproduct.put("productLabel",ResturantsProductsService.findLabelById(OrderProduct.getProductId()));
							
							List<Map> requiredAddOnsList=new ArrayList<>();
							List<CustomerOrderProductsRequiredAddOn> requiredAddOns=OrderProductsRequiredAddOnDao.findAllByOrderProductId(OrderProduct.getId());
							if(!requiredAddOns.isEmpty()) {
								requiredAddOns.stream().forEach(
										requiredAddOn->{
											Map RowproductRequiredAddon=new HashMap<>();
											RowproductRequiredAddon.put("price", requiredAddOn.getPrice());
											RowproductRequiredAddon.put("id", requiredAddOn.getProductId());
											RowproductRequiredAddon.put("productLabel",ResturantsProductsService.findLabelById(requiredAddOn.getProductId()));
											requiredAddOnsList.add(RowproductRequiredAddon);
											
											
										}
								);
							}
							Rowproduct.put("requiredAddOn", requiredAddOnsList);
							
							List<Map> optionalAddOnsList=new ArrayList<>();
							List<CustomerOrderProductsOptionalAddOn> optionalAddOns=OrderProductsOptionalAddOnDao.findAllByOrderProductId(OrderProduct.getId());
							if(!optionalAddOns.isEmpty()) {
								optionalAddOns.stream().forEach(
										optionalAddOn->{
											Map RowproductOptionalAddon=new HashMap<>();
											RowproductOptionalAddon.put("price", optionalAddOn.getPrice());
											RowproductOptionalAddon.put("id", optionalAddOn.getProductId());
											RowproductOptionalAddon.put("productLabel",ResturantsProductsService.findLabelById(optionalAddOn.getProductId()));
											optionalAddOnsList.add(RowproductOptionalAddon);
											
											
										}
								);
							}
							Rowproduct.put("optionalAddOn", optionalAddOnsList);
							
							orderProductsList.add(Rowproduct);
								
						}
					);
				}
				
				Row.put("products",orderProductsList);
				
				list.add(Row);
		}
			
			
		
		
		
		return list;
	}
	
	
	public void changeOrderStatus(long orderId,long statusId) {
		
		
		CustomerOrder order=OrderDao.findById(orderId);
		if(order!=null) {
			order.setOrderStatusId(statusId);
			OrderDao.save(order);	
		}
		
		
	}

	public List<Map> getMostPopularProducts(int limit) {
		List<Map> list=new ArrayList<>();
		
		List<Long> mostOrderedProducts=new ArrayList<>();
		if(limit==0) {
			mostOrderedProducts=OrderDao.findAllPopularProductsByIsActiveAndIsDeleted(1,0);
		}else{
			mostOrderedProducts=OrderDao.findAllPopularProductsByIsActiveAndIsDeletedAndLimit(1,0,limit);
		}
				
		if(!mostOrderedProducts.isEmpty()) {
			
			mostOrderedProducts.stream().forEach(
					mostOrderedProduct->{
					CommonResturantsProducts Product=ResturantsProductsDao.findById(utility.parseLong(mostOrderedProduct));
					if(Product!=null) {
						Map Row=new HashMap<>();
						Row.put("id", Product.getId());
						
						Row.put("label",Product.getLabel());
						Row.put("description", Product.getDescription());
						Row.put("resturantId", Product.getResturantId());
						Row.put("salesTax", Product.getSalesTax());
						Row.put("salesTaxPercentage", Product.getSalesTaxPercentage());
						Row.put("grossAmount", Product.getGrossAmount());
						Row.put("netAmount", Product.getNetAmount());
						Row.put("discount", Product.getDiscount());
						Row.put("rate", Product.getRate());
						
						
						if(Product.getIsTimingEnable()==0) {
							Row.put("isTimingEnable", Product.getIsActive());
							Row.put("isTimingEnableLable", "No");
							Row.put("availabilityFrom", "");
							Row.put("availabilityTo", "");
							
						}else {
							Row.put("isTimingEnable", Product.getIsActive());
							Row.put("isTimingEnableLable", "Yes");
							
							System.out.println(Product.getAvailabilityFrom());
							
							Row.put("availabilityFrom", DateUtils.addHours(Product.getAvailabilityFrom(), 5).getTime());
							
							try { 
								Row.put("availabilityFromDisplay", utility.millisecondstoTime(DateUtils.addHours(Product.getAvailabilityFrom(), 5).getTime()));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								Row.put("availabilityFromDisplay", "00:00:00");
								
							}
							
							Row.put("availabilityTo", DateUtils.addHours(Product.getAvailabilityTo(), 5).getTime());
							
//						
						}
						
						
						
						Row.put("isAvailable", Product.getIsAvailable());
						if(Product.getIsActive()==0) {
							Row.put("isActive", Product.getIsActive());
							Row.put("isActiveLabel", "In-Active");
						}else {
							Row.put("isActive", Product.getIsActive());
							Row.put("isActiveLabel", "Active");
						}
						Row.put("productImgUrl", Product.getProductImgUrl());
						Row.put("rating", Product.getRating());
						Row.put("resturantCategoryId", Product.getResturantCategoryId());
						Row.put("resturantCategoryLabel", ResturantsCategoriesService.findLabelById(Product.getResturantCategoryId()));
						if(Product.getIsExtra()==0) {
							Row.put("isExtraLabel","No");
							Row.put("isExtra",Product.getIsExtra());
						}else {
							Row.put("isExtraLabel","Yes");
							Row.put("isExtra",Product.getIsExtra());
							 
							
						}						
						list.add(Row);
					}
					
					
			
				}
			);	
				
				
				
		}
			
			
		
		
		
		return list;
	}
	

}
