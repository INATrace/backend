# INATrace

Open-source blockchain-based track and trace system for an agricultural commodities (such as coffee) supply
chain run. It provides transparency and creation of trust through
digitalization of supply chains, connects every actor along the supply chain, assures quality and fair pricing.

Project is composed from 3 parts:

* [Angular frontend](https://github.com/INATrace/fe/tree/main)
* [Java backend](https://github.com/INATrace/backend/tree/main)
* [Coffee network](https://github.com/INATrace/coffee-network/tree/main)

# INATrace 2
This new major release includes new functionalities, refactorings, optimizations and bugfixes. The most important additions and changes are:
* Added support for generic value chains. Different value chains with it's specific settings can now be configured in the system.
* Multi-tenant system support.
* Reorganized the content in the Product section. This section now includes only the content that is related to a product.
* Introduced a new section "Company". This section includes all the content that is related with the company's work process within the value chain.
* The configuration of farmers and collectors is decoupled from the product, and it's part of the Company section.
* Added support for importing farmers from a provided Excel file.
* The company customers are now decoupled from the Stakeholders in the product section and are configured as part of the Company section.
* The configuration of facilities and processing actions is now part of the company's profile.
* The semi-products configuration is now part of the system settings instead of the product section.
* Reorganized the content of the Value chain tab inside the Stakeholders section. The Value chain now includes new company roles. Added is also a section for product admin companies.
* Added support for defining Processing evidence fields in the system settings.
* Translation for facilities, processing actions, semi-products and processing evidence types and fields can be provided in the system as part of it's definition.
* Added support for currencies in the system. The enabled currencies can be selected in the system settings. These currencies then appear as select options in various parts of the system where the user is expected to select a currency.
* Added exchange rates for the enabled currencies that are synced on a daily basis. The currencies data is provided by the https://exchangeratesapi.io/ API.
* The product section now includes Final products. Final products represent the output of a final processing. The final products can be configured by the product admin company.
* When placing customer order, now we select a final product instead of a sellable semi-product.
* Added support for new types of processing actions.
* Added support for bulk purchases for semi-products.
* Various changes and addition of new functionalities for purchases, processing and payments.


# Backend

## Installing / Getting started

#### Requirements
* Java 11+
* maven
* mysql

#### How to run
1. Clone the repository

2. Import as maven project to your preferred IDE

3. Create an account and get an API key at https://exchangeratesapi.io/

4. Prepare environment, including database, as suggested in file `application.properties.template`

5. Run `INATraceBackendApplication.java` 

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
