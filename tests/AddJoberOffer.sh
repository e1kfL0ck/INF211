#!/bin/bash

echo "Creating Company";
curl "localhost:8080/api/v1/companies/create?mail=mnc@imt.fr&password=2580&city=Brest&name=myFirstCompany&description=Desc%20of%20my%20new%20company";

echo "Creating JobOffer 1";
curl "localhost:8080/api/v1/joboffer/create?companyId=1&title=testeur%20%C3%A9mile&description=Description%20de%20l%27offre&qualificationlevel=2&sectorIds=7&sectorIds=12";

echo "Creating JobOffer 2"
curl "localhost:8080/api/v1/joboffer/create?companyId=1&title=Validation&description=Validation%20produits&qualificationlevel=4&sectorIds=7&sectorIds=12"

echo "Please check the content of the db, then press : "
echo "C to delete the company"
echo "1 to delete the first joboffer"
echo "2 to delete the second joboffer"

read -r input

case $input in
  C|c)
    echo "Deleting Company..."
    curl -X DELETE "localhost:8080/api/v1/companies/1"
    echo "Company deleted."
    ;;
  1)
    echo "Deleting first Job Offer..."
    curl -X DELETE "localhost:8080/api/v1/joboffer/1"
    echo "First job offer deleted."
    ;;
  2)
    echo "Deleting second Job Offers..."
    curl -X DELETE "localhost:8080/api/v1/joboffer/2"
    echo "Second job offer deleted."
    ;;
  Q|q)
    echo "Exiting script."
    exit 0
    ;;
  *)
    echo "Invalid option. Exiting."
    exit 1
    ;;
esac

echo "Operation completed."