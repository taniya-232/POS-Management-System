## Swagger URL:
http://localhost:8080/swagger-ui/index.html

## CSRF disabling code added

## Implementing same return format

## Handling proper error format with exception class

## Updating updateCompany from PUT to PATCH to update specific field

## Added company create and update validations.
## ================================
## AVAILABLE VALIDATION ANNOTATIONS
## ================================
Annotation	Purpose
----------  -------
@NotNull	Cannot be null
@NotBlank	Cannot be null/empty/whitespace
@NotEmpty	Collection/string not empty
@Size	Length validation
@Email	Email format
@Min / @Max	Numeric range
@Pattern	Regex validation

## Added environment properties

## Modal Textbox functionality fixed.


Company -> Product
1		-> n
| Method | Endpoint                          |
| ------ | --------------------------------- |
| POST   | /api/products                     |
| GET    | /api/products                     |
| GET    | /api/products/{id}                |
| GET    | /api/products/company/{companyId} |
| PATCH  | /api/products/{id}                |
| DELETE | /api/products/{id}                |

SHOW CREATE TABLE products;

INSERT INTO units(uname,uabbr,`active`,deleted,created_at,updated_at) VALUES('Piece','Pc',TRUE,FALSE,NOW(),NOW());
INSERT INTO units(uname,uabbr,`active`,deleted,created_at,updated_at) VALUES('Gram','Gm',TRUE,FALSE,NOW(),NOW());

UPDATE products SET unit_id = 1;

Bootstrap Problem in authentication systems.

Password Recovery Architecture:
1. Forgot password
2. Enter valid email
3. Verify email exists
4. Generate 6 digit OTP
5. Store OTP + expiry time
6. Send email
7. User receives the OTP and enters email and OTP on the OTP validation window.
8. Verify OTP
9. Verify OTP expiry
10. New and Confirm Password when submitted then Encrypt password (BCrypt)
11. Update database
12. Clean OTP
