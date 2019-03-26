#include <iostream>
#include <string>

using namespace std;

class Ticket {
	
	private:
		string eventTitle;
		string seller;
		float ticketPrice;
		int quantity;
		bool canBeBought;
		
	public:

		Ticket(void) {
			this->canBeBought = false;
			this->seller = "false";
		}

		void setEventTitle(string eventTitle)
		{
			this->eventTitle = eventTitle;
		};
		void setSeller(string seller)
		{
			this->seller = seller;		
		};
		void setPrice(float ticketPrice)
		{
			this->ticketPrice = ticketPrice;
		};
		void setQuantity(int quantity)
		{
			this->quantity = quantity;
		};
		void setCanBeBought(bool canBeBought){
			this->canBeBought = canBeBought;
		};

		string getEventTitle() { return eventTitle;		};
		string getSeller() { return seller;		};
		float getPrice() { return ticketPrice;		};
		int getQuantity() { return quantity;	};
		bool getCanBeBought() { return canBeBought; };
};