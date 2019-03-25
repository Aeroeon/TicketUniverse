#include <unordered_map>
#include <vector>
#include <string>
#include <stdexcept>

#include "User.cpp"
#include "Ticket.cpp"

#include <ctime>
#include <fstream>
#include <sstream>
#include <math.h>
#include <iomanip> // setprecision

using namespace std;

class App {
  private:
    User activeUser;
    unordered_map< string, User > users;
    unordered_map< string, vector<Ticket> > tickets; // might need to be an array of tickets so that more than one seller can sell a ticket
    vector<string> Transactions;
    
    //logs
	ofstream transactionLog;
	// current date and time
	time_t currentTime = time(0);

    bool isUserPermitted(string type, string action) {
      // FS is Full standard
      // BS is Buy standard
      // SS is Sell standard
      // AA is admin
      if (type == "FS" && action == "DELETE") return false;
      else if (type == "FS" && action == "CREATE") return false;
      else if (type == "FS" && action == "REFUND") return false;
      else if (type == "FS" && action == "ADD_CREDIT") return false;

      else if (type == "BS" && action == "SELL") return false;
      else if (type == "BS" && action == "CREATE") return false;
      else if (type == "BS" && action == "REFUND") return false;
      else if (type == "BS" && action == "DELETE") return false;
      else if (type == "BS" && action == "ADD_CREDIT") return false;

      else if (type == "SS" && action == "BUY") return false;
      else if (type == "SS" && action == "CREATE") return false;
      else if (type == "SS" && action == "REFUND") return false;
      else if (type == "SS" && action == "DELETE") return false;
      else if (type == "SS" && action == "ADD_CREDIT") return false;

      
      
      return true;
    }

    public: 

    
      // creates a user with the specified username of length 15 and sets it's credits to 0
      /**
       * Create User
       * @Param: User Data Map
       * @Map Params: name, type, credit
       * 
       * updates user object state
       * adds transaction to Transaction array
       */
      void createUser(unordered_map<string, string> userData)
      {
        string type = activeUser.getType();
        
        if (!isUserPermitted(type, "CREATE")) {
          cout << "You cannot create users"; 
          return;
        }



        string userName = userData["name"];
        string userType = userData["type"];
        float userCredit = ::stoi( userData["credit"] );

        string transaction = "01 " + userName + " " + userType + " " + userData["credit"];

        User newUser;
        newUser.setCredit(userCredit);
        newUser.setName(userName);
        newUser.setType(userType);

        
        users[userName] = newUser;
        Transactions.push_back(transaction);
      }


      /**
       * Delete User
       * @Param: username
       * 
       * updates user object state
       * adds transaction to Transaction array
       */
      void deleteUser(string userName)
      {
        string type = activeUser.getType();

        if (!isUserPermitted(type, "DELETE")) {

          cout << "You cannot delete users or yourself"; 
          return;
        }
        
        string userType = users[userName].getType();
        string userCredit = to_string( users[userName].getCredit() );

        string transaction = "02 " + userName + " " + userType + " " + userCredit;

        users.erase(userName);

        Transactions.push_back(transaction);
      }

      /**
       * Sell Tickets
       * @Param: Ticket Data Map
       * @Map Params: title, seller, price, qty
       * 
       * updates Ticket object state
       * adds transaction to Transaction array
       */

      void sellTickets(unordered_map<string, string> ticketData)
      {
        //todo: must be edited
        string type = activeUser.getType();

        if (!isUserPermitted(type, "SELL")) {

          cout << "You cannot SELL tickets"; 
          return;
        }

        string title = ticketData["title"];
        string seller = ticketData["seller"];
        float price = stof( ticketData["price"] );
        int qty = stoi( ticketData["qty"] );

        string transaction = "02 " + title + " " + seller + " " + ticketData["price"] + " " + ticketData["qty"];

        
        Ticket ticket;
        
        ticket = getTicket(ticketData);

        if(tickets.find(seller) == tickets.end() || ticket.getSeller() == "false") {

          ticket.setEventTitle(title);
          ticket.setSeller(seller);
          ticket.setPrice(price);
          ticket.setQuantity(qty);
        } else {
          ticket.setQuantity(qty + ticket.getQuantity());
        }

        updateTickets(ticket);
        Transactions.push_back(transaction);
      }

      /**
       * Buy Tickets
       * @Param: Ticket Data Map
       * @Map Params: title, seller, price, qty
       * 
       * updates Ticket object state
       * adds transaction to Transaction array
       */
      Ticket buyTickets(unordered_map<string, string> ticketData)
      {
        // NOTE: needs to check whether ticket was made this session
        string type = activeUser.getType();
        Ticket ticket;
        
        if (!isUserPermitted(type, "BUY")) {

          cout << "You cannot buy tickets"; 
          return ticket;
        // throw std::invalid_argument("Unotherized User");
        }

        string title = ticketData["title"];
        string seller = ticketData["seller"];
        int qty = stoi(ticketData["qty"]);
      
        string transaction = "02 " + title + " " + seller + " " + ticketData["price"] + " " + ticketData["qty"];

        
        // NOTE: will throw errors if tickets can't be purchased. OR return a ticket if found
        ticket = getTicket(ticketData);

        if (qty > 4) {
          cout << "you can't purchase more than 4 tickets at once"; return ticket;
        }
        else if ( ticket.getQuantity() < qty ) {
          cout << "There are only " << ticket.getQuantity() << " tickets left."; return ticket;
        }
        
        ticket.setQuantity( ticket.getQuantity() - qty );
        
        updateTickets(ticket);
        Transactions.push_back(transaction);

        return ticket;
      }

      /**
       * Add Credit
       * @Param: amount, username
       * 
       * checks if active User is the 
       * adds transaction to Transaction array
       */
      void addCredit(float amount = 0, string username = "")
      {
        User user;
        username = (username != "") ? username : activeUser.getName();

        string transaction = "06 " + username + " " + user.getType() + " ";

        string type = activeUser.getType();

        if (!isUserPermitted(type, "ADD_CREDIT") && username != "") {

          cout << "Cannot add credit to other users"; 
          return;
        }
          // throw std::invalid_argument("User cannot add credit to others");
        if (username == "")
          activeUser.ADD_CREDIT(amount + activeUser.getCredit());
        else {

          user = users[username];
          user.ADD_CREDIT(amount + user.getCredit());
        }

        transaction += to_string(user.getCredit());

        Transactions.push_back(transaction);
        activeUser = user;
      }



      void updateTickets(Ticket ticket) // ticketData isn't necessary
      {
        // NOTE: Might be helpful to use binary search | set the id of each ticket to the sellers integer representation of the sellers name and ticket title
        // tickets should probably be a simple array of ticket objs. this is kinda stupid for the logout process.
        vector<Ticket> ticketSet;
        
        string seller = ticket.getSeller();
        // check to see if the seller exist
        // add seller if he doesn't exist
        if (tickets.find(seller) == tickets.end()) {

          tickets[seller].push_back(ticket); 
          return;
        }

        // ticketSet is only tickets belonging to specified seller
        ticketSet = tickets[seller];
        
        // find and update ticket
        for (int i = 0, size = ticketSet.size(); i < size; i++)
        {
          // cout << "loop: "<< i << " -> " <<  " " << ticketSet[i].getEventTitle() << " " << ticket.getEventTitle() <<"\n";
          // replace old ticket with new one
          if (ticketSet[i].getEventTitle() == ticket.getEventTitle()) {

            ticketSet[i] = ticket; 
            tickets[seller] = ticketSet;
            return;
          }
        }

        
        //if not found then just add to their tickte list
        ticketSet.push_back(ticket);
        tickets[seller] = ticketSet;
      }

      /**
       * Get Ticket
       * @Param: tickets, ticketData
       * 
       * check if seller exists if not returns empty ticket
       * adds transaction to Transaction array
       */
      //TODO: DOING TO MUCH MAKE SIMPLER needs work
      Ticket getTicket(unordered_map<string, string> ticketData)
      {
        // NOTE: Might be helpful to use binary search
        // can't handle some of these errors.

        Ticket ticket;
        vector<Ticket> ticketSet;

        // check to see if the seller exist
        if ( tickets.find(ticketData["seller"]) == tickets.end() )
          return ticket;
          // cout << "Seller doesn't exist";
          // throw std::invalid_argument("Seller doesn't exist");

        ticketSet = tickets[ticketData["seller"]];

        // check to see if ticket exists
        for (int i = 0, size = sizeof(ticketSet); i < size; i++)
        {
          /* code */
          if (ticketSet[i].getEventTitle() != ticketData["title"])
            continue;
            // throw std::invalid_argument("there aren't enough tickets");

          ticket = ticketSet[i];
        }

        return ticket;
      }
      
      
      	/**
       * Transaction ending
       * 
       * Logs afterwards
       */
		void displayTransaction(){
	    	currentTime = time(0); // Gets current time
	      	
	      	tm * ptm = localtime(&currentTime);
	      	char buffer[32];
	      	strftime(buffer, 32, "%Y-%m-%d_%H-%M-%S", ptm) ;
	      	cout << buffer << endl;
	      	string pathname = "./logs/" + string(buffer) + ".txt";//Daily Transaction will release be named by YYYY-MM-DD_HH-MM-SS
	      	transactionLog.open(pathname, ios::app); //Appends the file
			
			for (int count = 0; count < Transactions.size(); count++){
	            cout << Transactions[count] << endl;
	            transactionLog << Transactions[count] << endl;
	        }
			
			Transactions.clear();
			
			
			transactionLog.close(); // Closes the file for later
	      	
		}
      
      /**
       * Login system
       * @Param: input Name
       * 
       * checks if the username entered exists from the users account file
       */
      void login(string inputName) {
			
			//Checks if there is a currently logged-in user, to give error message
			if (!activeUser.getName().empty()) {
				cout << "You are already logged-in. Log out first to log in another account!" << endl;
				return;
			}
			
			// Check if exists and then open the file.
			ifstream inputFile("./db/users/users.db");        // Input file stream object
		    if (inputFile.good()) {
		        // Push items into a vector
		        string current_line;
		        while (getline(inputFile, current_line)){
		        	//Setting each value from user file into a vector array
		        	users[trim(current_line.substr(0,15))].setName(current_line.substr(0,15));
		        	users[trim(current_line.substr(0,15))].setType(current_line.substr(16,2));
		        	users[trim(current_line.substr(0,15))].setCredit(stof(current_line.substr(19,6)));
		        	
		        }
		
		        // Close the file.
		        inputFile.close();
		    	
		    	unordered_map< string, User >::iterator it = users.find(inputName);
		    	if (it == users.end()){ //checks if user exists
		    		cout << "User does not exist!" << endl; return;
				}
          		else {
          			activeUser.setName(it->second.getName())  ;
          			activeUser.setType(it->second.getType()) ;
          			activeUser.setCredit(it->second.getCredit()) ;
				}
          			
		
				cout << "User: " << activeUser.getName() << endl;
		        cout << endl;
		    }else {
		        cout << "Error, user file does not exist!";
		        _exit(0);
		    }
			
			
		}
	
		void logout() {
			//If user logs out without being logged in first
			if (activeUser.getName().empty()) {
				cout << "You are not logged-in" << endl; return;
			}
			
			cout << "Logging out" << endl;
			stringstream stream;
			stream << fixed << setprecision(2) << activeUser.getCredit(); 
			Transactions.push_back("00 " + activeUser.getName() + " " + activeUser.getType() + " " + stream.str());
			
			(&activeUser)->~User(); //Clears user object
			new (&activeUser) User(); //Initiallizes 
			
			displayTransaction();
			
		}
		
		//Used to trim any excess white space around the string
		string trim(const string& str)
		{
		    size_t first = str.find_first_not_of(' ');
		    if (string::npos == first)
		    {
		        return str;
		    }
		    size_t last = str.find_last_not_of(' ');
		    return str.substr(first, (last - first + 1));
		}
      
      
};
