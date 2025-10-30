# 💳 Paymob Payment Integration

This project implements a payment gateway integration using **Paymob API** in a Spring Boot application.  
It handles the complete payment process, including:  
- Authentication to get the Paymob token  
- Creating orders  
- Generating payment keys  
- Handling callback responses from Paymob  

---

## ⚙️ Tech Stack
- **Java 24**  
- **Spring Boot 3**  
- **REST API Integration**  
- **Lombok**  
- **Dotenv** for environment variables  

---

## 🚀 How to Run
1. Create a `.env` file in the project root and add the following:
   ```env
   API_KEY=your_api_key
   PAYMOB_INTEGRATION_ID=your_integration_id
   PAYMOB_IFRAME_BASE_URL=https://accept.paymob.com/api/
   PAYMOB_IFRAME_ID=your_iframe_id
   PAYMENT_HMAC_KEY_PAYMOB=your_hmac_key

   
   
