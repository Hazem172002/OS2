import java.awt.im.InputContext;
import java.util.Scanner;
import java.util.concurrent.*;

public class customer_service extends Thread {

	public static Semaphore clients = new Semaphore(0);
	public static Semaphore customer_service = new Semaphore(0);
	public static Semaphore chairs = new Semaphore(1);
	 int freeSeats = numbers.y;

	class Customer extends Thread {
		
		int customer;
		boolean needsServe = true;

		public Customer(int i) {
			customer = i;
		}

		public void run() {
			while (needsServe) {
				try {
					chairs.acquire();
					if (freeSeats > 0) {
						// If there are free seats, client serve.
						System.out.println("Customer " + this.customer + " makes a reservation.");
						freeSeats--;
						clients.release();
						// release chair
						chairs.release();
						try {
							customer_service.acquire();
							needsServe = false;
							this.service();
						} catch (InterruptedException ex) {
						}
				} else {
						// Handle no free seats
						System.out.println("No free seats  Customer service" + this.customer + " has left.");
						// release chair
						chairs.release();
						needsServe = false;
					}
				} catch (InterruptedException ex) {
				}
			}
		}

		public void service() {
			System.out.println("Customer service " + this.customer + " is getting to service");
			try {
				sleep(5050);
			} catch (InterruptedException ex) {
			}
		}

	}

	// Create barber threat
	class Customer_service extends Thread {


		public void run() {
			while (true) {
				try {
					clients.acquire();
					chairs.release();
					// Increment number of free seats
					freeSeats++;
					// customer service begins serve
					customer_service.release();
					// Unlock chairs
					chairs.release();
					// customer_service is served
					this.serve();
				} catch (InterruptedException ex) {
				}
			}
		}

		public void serve() {
			System.out.println("The customer service is serving");
			try {
				sleep(5000);
			} catch (InterruptedException ex) {
			}
		}
	}

	public static void main(String args[]) {
		 Scanner Customer = new Scanner(System.in);  
	     System.out.println("Enter number of clients:");
	     numbers.x = Customer.nextInt();       
	     System.out.println("Enter number of chairs:");
	     numbers.y = Customer.nextInt();

		 customer_service bank = new customer_service();
		// Start
		bank.start();
		
		    

	  
	}
	
    	public void run() {
		// Create instance of Customer_service
		Customer_service b = new Customer_service();
		// Start
		b.start();

		// Create scenario with x amount of clients, for this scenario set to 25
		for (int i = 1; i <= numbers.x ; i++) {
			Customer c = new Customer(i);
			c.start();
			try {
				sleep(2000);
			} catch (InterruptedException ex) {
			}
			;
		}
	}
		

}
class numbers{
public static int x;
public static int y;

}