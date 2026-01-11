import java.time.*;
import java.util.*;

public class VehicleManagementSystem {

static Scanner sc = new Scanner(System.in);

static class Vehicle {

int id;
String model;
String type;
boolean active = true;
boolean available = true;
double totalkm;
double purchaseValue;
LocalDate addedDate;

Vehicle(int id, String model, String type, double purchaseValue) {
this.id = id;
this.model = model;
this.type = type;
this.purchaseValue = purchaseValue;
this.addedDate = LocalDate.now();
}

}

}
public String toString() {

return id + " | " + model + " | " + type +
" | Active:" + active +
" | Available:" + available +
" | KM:" + totalkm +
" | Value:" + purchaseValue;

}

static class Driver {

int id;
String name;
boolean active = true;
boolean available = true;
int totalTrips;
double rating;
double penalties;
double bonuses;

Driver(int id, String name) {
this.id = id;
this.name = name;
}

}
public String toString() {

return id + "|" + name +
" | Trips:" + totalTrips +
" | Rating:" + rating +
" | Penalty:" + penalties +
" | Bonus:" + bonuses +
" | Active:" + active;

}

static class Customer {

int id;
String name;
int loyaltyPoints;
double creditLimit;
boolean blacklisted = false;
double outstanding;

Customer(int id, String name) {
this.id = id;
this.name = name;
}

}
String tier() {

if (loyaltyPoints >= 1000) return "Platinum";
if (loyaltyPoints >= 400) return "Gold";
return "Standard";

}

public String toString() {

return id + " | " + name +
" | Tier:" + tier() +
" | Points:" + loyaltyPoints +
" | Outstanding:" + outstanding +
" | Blacklisted:" + blacklisted;

}

static class Trip {

int id;
int vehicleId;
int driverId;
int customerId;
int km;
double rate;
double bill;
LocalDate date;
boolean completed;

}
}

Trip(int id, int v, int d, int c, int km, double rate) {

this.id = id;
this.vehicleId = v;
this.driverId = d;
this.customerId = c;
this.km = km;
this.rate = rate;
this.bill = km * rate;
this.date = LocalDate.now();

}

public String toString() {

return id + " | " + date +
" | V:" + vehicleId +
" | D:" + driverId +
" | C:" + customerId +
" | KM:" + km +
" | Rate:" + rate +
" | Bill:" + bill +
" | Done:" + completed;

}
static class FuelRecord {

int vehicleId;
double liters;
double cost;
LocalDate date;

FuelRecord(int v, double l, double c) {

vehicleId = v;
liters = l;
cost = c;
date = LocalDate.now();

}

}

static class Maintenance {

int vehicleId;
double cost;
String reason;
LocalDate date;
boolean breakdown;

}
Maintenance(int v, double c, String r, boolean b) {

vehicleId = v;
cost = c;
reason = r;
breakdown = b;
date = LocalDate.now();

}

}

static class Insurance {

int vehicleId;
LocalDate expiry;

Insurance(int v, LocalDate e) {

vehicleId = v;
expiry = e;

}

}

// ============================== DATA STORES ==============================

static List<Vehicle> vehicles = new ArrayList<>();
static List<Driver> drivers = new ArrayList<>();
static List<Customer> customers = new ArrayList<>();
static List<Trip> trips = new ArrayList<>();
static List<FuelRecord> fuels = new ArrayList<>();
static List<Maintenance> maintenances = new ArrayList<>();
static List<Insurance> insurances = new ArrayList<>();
static List<String> auditLogs = new ArrayList<>();

static int tripSequence = 1;

static void seedData() {

vehicles.add(new Vehicle(1, "Toyota Axio", "Car", 2500000));
vehicles.add(new Vehicle(2, "Hiace", "Microbus", 4200000));

drivers.add(new Driver(1, "Rahim"));
drivers.add(new Driver(2, "Karim"));

customers.add(new Customer(1, "ACME Ltd"));
customers.add(new Customer(2, "John Doe"));

insurances.add(new Insurance(1, LocalDate.now().plusDays(25)));
insurances.add(new Insurance(2, LocalDate.now().plusDays(120)));

}
static Vehicle findVehicle(int id) {

for (Vehicle v : vehicles) if (v.id == id) return v;

return null;

}

static Driver findDriver(int id) {

for (Driver d : drivers) if (d.id == id) return d;

return null;

}

static Customer findCustomer(int id) {

for (Customer c : customers) if (c.id == id) return c;

return null;

}

static void log(String msg) {

auditLogs.add(LocalDateTime.now() + " | " + msg);

}
static void addVehicle() {

System.out.print("ID: ");

int id = sc.nextInt();

sc.nextLine();

System.out.print("Model: ");

String m = sc.nextLine();

System.out.print("Type: ");

String t = sc.nextLine();

System.out.print("Purchase Value: ");

double pv = sc.nextDouble();

vehicles.add(new Vehicle(id, m, t, pv));

log("Vehicle added " + id);

}

static void retireVehicle() {

System.out.print("Vehicle ID: ");

int id = sc.nextInt();

Vehicle v = findVehicle(id);

if (v != null) {

v.active = false;

v.available = false;

log("Vehicle retired " + id);

}

}
static void rateDriver() {

System.out.print("Driver ID: ");

int id = sc.nextInt();

System.out.print("Rating (1-5): ");

double r = sc.nextDouble();

Driver d = findDriver(id);

if (d != null) {

d.rating = (d.rating + r) / 2;

log("Driver rated " + id);

}

}

static void blacklistCustomer() {

System.out.print("Customer ID: ");

int id = sc.nextInt();

Customer c = findCustomer(id);

if (c != null) {

c.blacklisted = true;

log("Customer blacklisted " + id);

}

}
static double calculateRate(Customer c, Vehicle v) {

double base = 30;

if (v.type.equalsIgnoreCase("Microbus")) base += 10;

if (c.tier().equals("Gold")) base -= 2;

if (c.tier().equals("Platinum")) base -= 5;

return base;

}

static void createTrip() {

System.out.print("Vehicle ID: ");

int vId = sc.nextInt();

System.out.print("Driver ID: ");

int dId = sc.nextInt();

System.out.print("Customer ID: ");

int cId = sc.nextInt();

System.out.print("KM: ");

int km = sc.nextInt();

Vehicle v = findVehicle(vId);

Driver d = findDriver(dId);

Customer c = findCustomer(cId);

}
}

Vehicle v = findVehicle(vId);

Driver d = findDriver(dId);

Customer c = findCustomer(cId);

if (v == null || d == null || c == null || !v.available || !d.available || c.blacklisted) {

System.out.println("Trip rejected by governance rules.");

return;

}

double rate = calculateRate(c, v);

Trip t = new Trip(tripSequence++, vId, dId, cId, km, rate);

trips.add(t);

v.available = false;

d.available = false;

v.totalkm += km;

d.totalTrips++;

c.loyaltyPoints += km / 3;

c.outstanding += t.bill;

log("Trip created " + t.id);

System.out.println("Trip created. Bill: " + t.bill);

}

static void completeTrip() {

System.out.print("Trip ID: ");
static void completeTrip() {

System.out.print("Trip ID: ");

int id = sc.nextInt();

for (Trip t : trips) {

if (t.id == id && !t.completed) {

t.completed = true;

findVehicle(t.vehicleId).available = true;

findDriver(t.driverId).available = true;

log("Trip completed " + id);

return;

}

}

}

========= COST MODULE =====

static void addFuel() {

System.out.print("Vehicle ID: ");

int id = sc.nextInt();

System.out.print("Liters: ");

double l = sc.nextDouble();

System.out.print("Cost: ");

double c = sc.nextDouble();

fuels.add(new FuelRecord(id, l, c));

}
