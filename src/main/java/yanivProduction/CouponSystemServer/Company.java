package yanivProduction.CouponSystemServer;

import java.util.Calendar;
import java.util.HashSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import clientFacade.ClientType;
import clientFacade.CompanyFacade;
import javaBeans.Coupon;
import javaBeans.CouponType;
import webJavaBeans.WebCoupon;

@Path("/company")
public class Company {

	private CompanyFacade getFacade() {
		CompanyFacade company = new CompanyFacade().login("company-api-test", "company-api-test", ClientType.COMPANY);
		return company;

	}

	@GET
	@Path("/coupon/all")
	@Produces("application/json")
	public HashSet<WebCoupon> getAllCoupons() {

		CompanyFacade company = getFacade();

		HashSet<Coupon> coupons = company.getAllCoupons();
		System.out.println("here");
		HashSet<WebCoupon> webCoupons = new HashSet<>();
		for (Coupon c : coupons) {
			System.out.println(c);
			webCoupons.add(new WebCoupon(c));
		}

		return webCoupons;

	}

	@GET
	@Path("/coupon/date/{day}/{month}/{year}")
	@Produces("application/json")
	public HashSet<WebCoupon> getAllCouponsbyDate(
			@PathParam("year") int yyyy,
			@PathParam("month") int mm,
			@PathParam("day") int dd) {

		CompanyFacade company = getFacade();

		Calendar cal = Calendar.getInstance();
		cal.set(yyyy, mm, dd);
		java.util.Date endDate = cal.getTime();


		HashSet<Coupon> coupons = company.getCouponsByDate(endDate);
		HashSet<WebCoupon> webCoupons = new HashSet<>();
		for (Coupon coupon : coupons) {
			webCoupons.add(new WebCoupon(coupon));
		}
		return webCoupons;
	}

	@GET
	@Path("/coupon/price/{price}")
	@Produces("application/json")
	public HashSet<WebCoupon> getAllCouponsbyPrice(@PathParam("price") double price) {

		CompanyFacade company = getFacade();

		HashSet<Coupon> coupons = company.getCouponsByPrice(price);
		HashSet<WebCoupon> webCoupons = new HashSet<>();
		for (Coupon coupon : coupons) {
			webCoupons.add(new WebCoupon(coupon));
		}
		return webCoupons;
	}

	@GET
	@Path("/coupon/type/{type}")
	@Produces("application/json")
	public HashSet<WebCoupon> getAllCouponsbyType(@PathParam("type") CouponType type) {

		CompanyFacade company = getFacade();

		HashSet<Coupon> coupons = company.getCouponsByType(type);
		HashSet<WebCoupon> webCoupons = new HashSet<>();
		for (Coupon coupon : coupons) {
			webCoupons.add(new WebCoupon(coupon));
		}
		return webCoupons;
	}


	@POST
	@Path("/coupon")
	@Consumes("application/json")
	public void postCoupon(WebCoupon webcoupon) {

		CompanyFacade company = getFacade();

		Coupon coupon = webcoupon.getCoupon();
		company.createCoupon(coupon);
	}

	@PUT
	@Path("/coupon")
	@Consumes("application/json")
	public void putCoupon(WebCoupon webcoupon) {

		CompanyFacade company = getFacade();

		Coupon coupon = webcoupon.getCoupon();
		company.updateCoupon(coupon);
	}

	@DELETE
	@Path("/coupon")
	@Consumes("application/json")
	public void deleteCoupon(WebCoupon webcoupon) {

		CompanyFacade company = getFacade();

		Coupon coupon = webcoupon.getCoupon();
		company.removeCoupon(coupon);
	}

}