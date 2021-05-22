# DocHolder
📜 A service for storing and presenting official documents of an organization.

# Description
Our graduation project from Netcracker Education Center || Saint Petersburg (Software Development)

[Slides on SlideShare](https://www.slideshare.net/ssuserfa68c1/docholder)


# How did we get the idea:
The task was to create a simple and at the same time
useful service for storing and managing company documents.
This project helped us study the standards and modern solutions
in the IT field. And so, the product is ready, welcome.

# FAQ (from our pre-defense/defense)
## Theory
### Why did you use Reverse Proxy?
— Reverse proxies provide anonymity for our backend server, not clients.
When using a forward proxy it's more about client anonymity, which wasn't our goal. 

### Hibernate vs JPA vs Spring Data?
- Hibernate is a library used to transform object model to relational model.
- JPA is an API that allows you to set the information necessary to transform objects to a relational database model using Java annotations, it contains the EntityManager, EntityTransaction interfaces.
- Spring Data - contains logic that allows you to abstract from specific databases, for example, for typical CRUD operations, there is a CrudRepository that allows you to perform most of the typical operations with almost any database (relational and non-relational); can be used without Hibernate, but Spring Data itself does not know how to convert objects to database data structures.

### What algorithm is used to encrypt user passwords and why?
— SHA - 256, it's key has not yet been compromised. 
Key length 256 bits, for input more than 2 exabytes.
SHA - 256 is used for: 
- emission of Bitcoin;
- X.509;
- DSA.
which instills confidence in our choice.

### What protocol is used for user authentication why is he? 
— We used Password based authentication, HTTP header to be more exact
it is the best option, while the standard Authorization header can also be used.

### How is the authorization of the user to the resources? Are the authorization policies fixed, or can the administrator change them?
— Answere above, polices are fixed

### RBAC or ABAC based authorization policies?
— Both of them

### Where is the JWT stored? 
— Local storage

### Difference between localstorage and specialstorage?
— Local Storage
 * Stores data indefinitely.
 * Cleared only by JavaScript or clearing the browser cache.
 * Stores data up to 5 MB, this is the largest of the three storage options.
 * Not supported by older browsers like IE 7 and below.
 * Operates according to the same origin policy. That is, the saved data is available only for one source.

— Session Storage
 * Stores data while the current session continues. When the user closes the browser, the data is no longer available.
 * The context of the top-level browser is used, so each browser tab stores unique data.
 * The amount of data is larger than the cookie.
 * Not supported by older browsers such as IE 7 and below.

— Cookie (Also is an option)
* Stores data that can be passed to the server via headers.
* Local and session storage are available only on the client side.
* The retention period is set when the cookie is created.
* The amount of data does not exceed 4 KB.
* Cookies can be secure, in which case their content cannot be retrieved on the client side. This is important for authentication when storing custom tokens.
* Recommended httpOnly Cookie, cause it protects from XSS

## Frontend
### Why you have choose Vue?
— Cause it's reactive (the program monitors changes to its data in real time). 
By using “reactivity”, we have create lightning fast interface that provide a better user experience. 
### Why not React or Angular?
— In Vue, component dependencies are automatically tracked during rendering, 
so the system knows exactly which components really need to be re-rendered when state changes, allowing you to focus more on building the application itself as it scales.

— Also another important difference between other frameworks and Vue is the fact that all additional Vue libraries, 
including libraries for managing application state and for routing (among other tasks), 
are officially maintained up to date according to the core of the library.)

### What could you use except pagination?  
— Infinite scroll - it loads content with each pass to the end of the page, convenient for mobile users

### Got Lazyloading? 
— Out of the box routerview 

## Backend
### Why Java 8?
— The minimum set required for the implementation of our RESTfuk API (I'd say Gentleman's set):
- Long support
- Lambdas
- Static Methods
- Duplicate annotations
- Enhanced annotation support

### Why Swagger and not Open Api?
— Swagger 2.0 and OpenApi (Swagger 3.0) differ in syntax and capabilities. 
Components and support for Multiple Servers \ Hosts appeared in 3.0
So why we decided to use more common solution.

### How do I get the secret? 
— Spring security, Through arguments, Through configuration files

# Install
<pre>
   <code>
      ...\DocHolder> docker-compose build; docker-compose up --no-start  
      ...\DocHolder> docker-compose start    
   </code>
</pre>
![image](https://user-images.githubusercontent.com/47406394/110204994-2288af80-7e87-11eb-99db-463bc3974244.png)
