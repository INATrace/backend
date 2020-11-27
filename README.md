# INATrace

Open-source blockchain-based track and trace system for an agricultural commodities (such as coffee) supply
chain run. It provides transparency and creation of trust through
digitalization of supply chains, connects every actor along the supply chain, assures quality and fair pricing.

Project is composed from 3 parts:

* [Java backend](https://github.com/INATrace/backend/tree/main) (Authentication, User, Product, Company APIs)
* [Coffee network](https://github.com/INATrace/coffee-network/tree/main) (StockOrder, Transaction, Order, SemiProduct APIs)
* [FE](https://github.com/INATrace/fe/tree/main)


# Backend

## APIs
Most common API used:

- Create a new user (not activated): 
`POST /api/user/register`

```
request {
	"email": "string",
	"password": "string",
	"name": "string",
	"surname": "string"
	}
```

- Log in a user :
`POST /api/user/login`

```
request {
	"username": "string",
	"password": "string"
	}
```
  
- Create a new company (with the logged-in user as company admin or update company 
`POST /api/company/create` or `PUT /api/company/profile`

```
request {
	"id":"integer",
	"name":"string",
	"abbreviation":"string",
	"logo":"ApiDocument",
	"headquarters":"ApiAddress",
	"about":"string",
	"manager":"string",
	"webPage":"string",
	"email":"string",
	"phone":"string",
	"mediaLinks":{},
	"interview":"string",
	"documents": ApiCompanyDocument,
	"certifications": ApiCertification
	}
```

- Create or update a product
`POST /api/product/create` or `PUT /api/product`

```
request {
	"id":"integer",
	"name":"string",
	"photo":"ApiDocument",
	"description":"string",
	"ingredients":"string",
	"nutritionalValue":"string",
	"howToUse":"string",
	"origin": ApiProductOrigin,
	"keyMarketsShare":{},
	"process": ApiProcess,
	"responsibility": ApiResponsibility,
	"sustainability": ApiSustainability,
	"associatedCompanies": ApiProductCompany,
	"company": ApiCompany,
	"labels": ApiProductLabelValues,
	"specialityDocument": ApiDocument,
	"specialityDescription":"string",
	"settings": ApiProductSettings,
	"comparisonOfPrice": ApiComparisonOfPrice,
	 "knowledgeBlog":"boolean"
	}
```

Response is structured in following way:
It always contains attribute [`status`](https://github.com/INATrace/backend/blob/main/src/main/java/com/abelium/INATrace/api/ApiStatus.java).
If response is successful, then `status` is equal to 'OK' and appropriate response can be found under `data` attribute.
If response is unsuccessful (see above link for other statuses), then `errorMessage` attribute is returned.

Example of successful and unsuccessful response
```
{
"status": "OK",
  "data": {
    "id": 4,
    "email": "example@example.com",
    "name": "Example",
    "surname": "Example",
    "status": "ACTIVE",
    "role": "ADMIN",
    "actions": [
      "VIEW_USER_PROFILE",
      "UPDATE_USER_PROFILE"
    ],
    "companyIds": [
	1
    ]
  }
}
```

```
{
  "status": "AUTH_ERROR",
  "errorMessage": "Invalid credentials"
}
```

Other APIs can be found [here](https://github.com/INATrace/backend/tree/main/src/main/java/com/abelium/INATrace/components)

Database entities are listed [here](https://github.com/INATrace/backend/tree/main/src/main/java/com/abelium/INATrace/db)

## Installing / Getting started

#### Requirements
* Java 11
* Spring Tools 4
* maven
* mysql

#### How to run
1. Clone the repository

2. Import as maven project to your prefered IDE

3. Prepare environment, including database, as suggested in file `application.properties.template`

4. Run `INATraceBackendApplication.java` 

## Contribution

Project INATrace welcomes contribution from everyone. See CONTRIBUTING.md for help getting started.

## License 

Copyright (c) 2020 Anteja ECG d.o.o., GIZ - Deutsche Gesellschaft für Internationale Zusammenarbeit GmbH

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as published
by the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
