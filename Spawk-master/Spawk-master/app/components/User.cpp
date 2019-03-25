#include <string>
#include <map>
#include <math.h>

using namespace std;

// app.action( user.ADD_CREDIT(10) )

class User {

  private:
    string type;
    string name;
    float credit;



  public:

    void ADD_CREDIT(float amount)
    {
      // todo: throw error if more than 2 decimals have been inputed
      
      this->credit = this->credit + amount;
    }


    void setType(string type) {

      this->type = type;
    }
    void setName(string name) {
      this->name = name;
    }
    void setCredit(float credit) {
      this->credit = credit;
    }
    
    string getType() {
      return this->type;
    }
    string getName() {
      return this->name;
    }
    float getCredit() {
      return this->credit;
    }
};