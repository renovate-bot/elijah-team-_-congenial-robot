//package tripleo.elijah;
//
//import io.avaje.jsonb.Json;
//import io.avaje.jsonb.JsonType;
//import io.avaje.jsonb.Jsonb;
//import org.junit.Test;
//
//import java.text.DateFormat;
//import java.util.Date;
//import java.util.LinkedHashMap;
//import java.util.Map;
//import java.util.UUID;
//
//@Json
//public class JSonBTest {
//
//	@Test
//	public void testOne() {
//		Jsonb jsonb = Jsonb.newBuilder().build();
//
//		JsonType<Customer> customerType = jsonb.type(Customer.class);
//
//
//		Customer customer = new Customer("joe");
//		String asJson = customerType.toJson(customer);
//
//		//String jsonContent = ...;
//		//Customer customer = customerType.fromJson(jsonContent);
//
//		String now = ((DateFormat.getDateTimeInstance())).format(new Date());
//
//		Map<String,Object> customerMap = new LinkedHashMap<>();
//		customerMap.put("id", 42L);
//		customerMap.put("name", "foo");
//		customerMap.put("whenCreated", now);
//
//		// nested collection
//		//Set<Map<String,Object>> contactMaps = null;
//		//customerMap.put("contacts", contactMaps);
//
//		Customer customer1 = jsonb.type(Customer.class).fromObject(customerMap);
//		System.err.println(customer1);
//
//	}
//
//	@Json
//	public record Address (String street, String suburb, City city) { }
//	//
//	@Json
//	public static class City {
//		public UUID getId() {
//			return id;
//		}
//
//		public void setId(final UUID aId) {
//			id = aId;
//		}
//
//		public String getName() {
//			return name;
//		}
//
//		public void setName(final String aName) {
//			name = aName;
//		}
//
//		public String getZone() {
//			return zone;
//		}
//
//		UUID   id;
//		String name;
//		String zone;
//
//		public City(UUID id, String name) {
//			this.id = id;
//			this.name = name;
//		}
//
//		public void setZone(String zone) {
//			this.zone = zone;
//		}
//		//plus getters ...
//	}
//
//	@Json
//	public static class Customer {
//		private String name;
//
//		public Customer(final String aName) {
//			name = aName;
//		}
//
//		public String getName() {
//			return name;
//		}
//
//		public void setName(String name) {
//			this.name = name;
//		}
//	}
//}
