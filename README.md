# Projekt aines Veebiteenused (IDU0075)

## Sisukord
* [Sissejuhatus](#sissejuhatus)
* [Teenuse põhiobjektid](#teenuse-põhiobjektid)
  - [Warehouse](#warehouse)
  - [Material](#material)
* [Teenuse põhioperatsioonid](#teenuse-põhioperatsioonid)
  - [Üldine informatsioon teenuse kohta](#Üldine-informatsioon-teenuse-kohta)
      + [token](#token)
      + [WSDL](#wsdl)
* [SOAP API kirjeldus](#soap-api-kirjeldus)
  - [SOAP API Operatsioonid](#soap-api-operatsioonid)
  - [SOAP API Operatsioonide kirjeldused](#soap-api-operatsioonide-kirjeldused)
      + [Error seisundid SOAP](#error-seisundid-soap)
* [REST API kirjeldus](#rest-api-kirjeldus)
  - [REST API Operatsioonid](#rest-api-operatsioonid)
  - [REST API Operatsioonide kirjeldused](#rest-api-operatsioonide-kirjeldused)
      + [Error seisundid REST](#error-seisundid-rest)
* [Seisundi koodide tähendused](#seisundi-koodide-tähendused)
* [Viited](#viited)



## Autor
Denis Rästas 155552IAPB

## Sissejuhatus
Antud dokumentatsiooni struktuur oli tehtud [Joosep Alviste](https://github.com/JoosepAlviste/veebiteenused-projekt) dokumentatsiooni põhjal.

Veebiteenused aine sooritamiseks on projekti tegemine kasutades [Netbeans-i](https://netbeans.org) ning [Glassfish-i](https://glassfish.java.net). 

Projekt koosneb neljast osast: dokumentatsioon, realisatsioon, testimine ning kaitsmine.

Dokumentatsiooni eesmärgiks anda ülevaade tehtud projekti teenusele ja selle SOAP ja REST API-dele.
Realisatsiooni tulemuseks on ladu SOAP API ja REST API teenus (`WarehouseWebService`) ning klientrakendus (`WarehouseApplication`) millega on võimalik SOAP API funktsionaalsust osaliselt katsetada.
Testimine toimub [SoapUI](https://www.soapui.org) ning [Postman-i](https://www.getpostman.com) kaudu.

Teenust kasutades saab lisada ja vaadata laod ja nende materjale.

***





## Teenuse põhiobjektid
Antud teenuse põhiobjektid on `Warehouse` ja `Material`.
Iga ladu ja materjal on unikaalne ehk teenusel ei tohi olla kaks või mitu sama nimega (või materjali puhul - koodiga) objekti.
Laos võib olla mitu materjali. Materjalid võivad kuuluda ladule aga see pole kohustuslik.
Kui ladule lisada sama materjali kaks korda toimub ülekirjutamine mis tähendab, et viimane jääb teenusel.

Üldine struktuuri jaotus on järgnev:
~~~
Service
├── Warehouse
│   └── Material
└── Material
~~~







### Warehouse
Teenuse piiredes võivad olla mitu ladu. Ladule võivad kuuluda mitu materjali. Laoga on ka seotud nimi ja aadress mille kaudu seda saab leida. Samuti määratakse selle ruumala ja pindala.

Ladu kirjeldavad järgmised atribuudid:
* **id** - unikaalne numbriline identifikaator, genereeritakse automaatselt süsteemi poolt;
* **warehouseName** - lao nimi;
* **warehouseAddress**  - lao aadress;
* **warehouseCapacity**  - lao ruumala;
* **warehouseArea** - lao pindala;
* **warehouseMaterialList** - laole kuuluvaid materjalid.

Üks võimalikest ladudest:
* **id** - 1;
* **warehouseName** - Tallinn Ladu;
* **warehouseAddress**  - Kalmistu tee 26;
* **warehouseCapacity**  - 500.00 m<sup>3</sup>;
* **warehouseArea** - 1000.00 m<sup>2</sup>;
* **warehouseMaterialList** - [].





### Material
Teenuse piiredes võivad olla mitu materjale. Igal materjalil on nimi ja selle koostis.
Samuti materjalile määratakse selle tugevus (näiteks, akna koostises on klaas seega tugevus - nõrk).
Materjali kood genereeritakse loomisel tehases ja *võib!* sisaldada informatsiooni.

Materjali kirjeldavad järgmised atribuudid:
* **id** - unikaalne numbriline identifikaator, genereeritakse automaatselt süsteemi poolt;
* **name** - materjali nimetus;
* **code** - materjali kood;
* **composition** - materjali koostis;
* **durability** - materjali tugevus.

Üks võimalikest materjalidest:
* **id** - 5;
* **name** - Window;
* **code** - WIN0001TLN20LDU;
* **composition** - Glass, plastic;
* **durability** - Low.

***





## Teenuse põhioperatsioonid
Mõlemad API-d pakuvad liidest ühele ja samale teenusele. See tähendab, et mõlema API-de operatsioonid on sarnased.
Olemasolevate operatsioonide nimekiri ja nende kirjeldus (mida sellega saab teha).

* **Ladu lisamine**
    - Saab lisada ladu.
* **Ühe ladu küsimine**
    - Saab küsida andmeid ühe lao kohta selle id järgi.
* **Kõikide ladude küsimine**
    - Saab küsida andmeid kõikide laode kohta.
* **Materjali lisamine**
    - Saab lisada materjali.
* **Ühe materjali küsimine**
    - Saab küsida andmeid ühe materjali kohta selle id järgi.
* **Kõikide materjalide küsimine**
    - Saab küsida andmeid kõikide materjalide kohta.
* **Materjali ladule lisamine**
    - Saab lisada olemasolevat materjali olemasolevasse lattu.
* **Ühe ladu kõikide materjalide küsimine**
    - Saab küsida kõikide materjalide nimekiri ja selle andmeid ühe lao kohta selle id järgi.





### Üldine informatsioon teenuse kohta

#### token
Realiseeritud projektis API-d kasutavad `token` autentimine.
Operatsiooni päringu tegemiseks kasutaja peab sisestama õige `token`-it.

**Näide:**
* Kasutaja saadab materjali loomise päringut kasutades `token`-i salajane.
* Teenuses salvestatakse materjal id-ga 1.
* Kasutaja saadab materjali loomise päringut kasutades `token` hapukapsas.
* Uut materjali ei looda, sest token on ebakorrektne.
* Kasutaja saadab materjali loomise päringut kasutades `token`-i salajane.
* Teenuses salvestatakse materjal id-ga 2.

#### WSDL
API-dega kasutatava WSDL faili tee `src/conf/xml-resources/web-services/WarehouseWebService/wsdl/WarehouseService.wsdl` mis samuti on saadaval ka *deploy*itud rakenduse põhjal lihsalt lisades URL-ile `?wsdl` ehk `(hostname)/WarehouseWebApplication/WarehouseService?wsdl`.

***





## SOAP API kirjeldus
API on saadaval lingina `(hostname)/WarehouseWebApplication/WarehouseService`.

### SOAP API Operatsioonid
Realiseeritud SOAP API teenus võimaldab täita järgmised operatsioonid:
* [addWarehouse SOAP](#addwarehouse-soap)
* [getWarehouse SOAP](#getwarehouse-soap)
* [getWarehouseList SOAP](#getwarehouselist-soap)
* [addMaterial SOAP](#addmaterial-soap)
* [getMaterial SOAP](#getmaterial-soap)
* [getMaterialList SOAP](#getmateriallist-soap)
* [addWarehouseMaterial SOAP](#addwarehousematerial-soap)
* [getWarehouseMaterialList SOAP](#getwarehousemateriallist-soap)

### SOAP API Operatsioonide kirjeldused
Iga päring peab sisaldama korrektne `token`.




***
#### addWarehouse SOAP
Lao lisamise operatsioon. Saab lisada lao millel on nimi ja aadress, selle ruumala ja pindala.

##### Sisendandmete kirjeldus
Elemendid päringus andmetüüpidega on järgnevad:
* `warehouse`
    - `warehouseName` - `string`, lao nimi;
    - `warehouseAddress` - `string`, lao aadress;
    - `warehouseCapacity` - `double`, valiidne lao ruumala (näiteks, 615.12 või 843 m<sup>3</sup>);
    - `warehouseArea` - `double`, valiidne lao pindala (näiteks, 3333.334 või 1500 m<sup>2</sup>).

###### Näidis SOAP päring (request)
~~~xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:war="http://www.ttu.ee/idu0075/warehouse">
   <soapenv:Header/>
   <soapenv:Body>
      <war:addWarehouseRequest>
         <war:token>salajane</war:token>
         <war:warehouseName>Tallinn Ladu</war:warehouseName>
         <war:warehouseAddress>Kalmistu tee 26</war:warehouseAddress>
         <war:warehouseCapacity>500</war:warehouseCapacity>
         <war:warehouseArea>1000</war:warehouseArea>
      </war:addWarehouseRequest>
   </soapenv:Body>
</soapenv:Envelope>
~~~

##### Väljundandmete kirjeldus
Õnnestunud päringu vastus ehk päringu `Success` seisund. Elemendid vastuses andmetüüpidega on järgnevad:
* `warehouse`
    - `id` - `integer`, loodud lao unikaalne identifikaator, genereeritakse süsteemi poolt;
    - `warehouseName` - `string`, loodud lao nimi;
    - `warehouseAddress` - `string`, loodud lao aadress;
    - `warehouseCapacity` - `double`, loodud lao ruumala;
    - `warehouseArea` - `double`, loodud lao pindala;
    - `warehouseMaterialList` - `warehouseMaterialListType`, tühi materjalide nimekiri.
* `state` - `stateType`, päringu seisund.
    - `success` - `successType`, - õnnestunud päringu seisundi element.
        + `code` - `integer`, õnnestunud päringu seisundi kood. `200` tähendab, et ladu on edukalt loodud;
        + `message` - `string`, lisainformatsioon operatsiooni seisundi kohta.

Kui päring ebaõnnestub, tagastatakse `Error` seisundi mille kohta informatsioon asub [Error seisundite SOAP-is](#error-seisundid-soap).

Oluline ka see, et topelt saadetud päringud tagastavad juba loodud ladu. 

###### Näidis SOAP vastus (response)
~~~xml
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
   <S:Body>
      <addWarehouseResponse xmlns="http://www.ttu.ee/idu0075/warehouse">
         <warehouse>
            <id>1</id>
            <warehouseName>Tallinn Ladu</warehouseName>
            <warehouseAddress>Kalmistu tee 26</warehouseAddress>
            <warehouseCapacity>500.0</warehouseCapacity>
            <warehouseArea>1000.0</warehouseArea>
            <warehouseMaterialList/>
         </warehouse>
         <state>
            <success>
               <code>200</code>
               <message>OK. Warehouse successfully added!</message>
            </success>
         </state>
      </addWarehouseResponse>
   </S:Body>
</S:Envelope>
~~~




***
#### getWarehouse SOAP
Lao küsimise operatsioon. Ladu saab otsida selle unikaalse identifikaatori ehk `id` järgi.

##### Sisendandmete kirjeldus
Päringus on ainult üks väärtus:
* `id` - `integer`, küsitava ladu id.

###### Näidis SOAP päring (request)
~~~xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:war="http://www.ttu.ee/idu0075/warehouse">
   <soapenv:Header/>
   <soapenv:Body>
      <war:getWarehouseRequest>
         <war:token>salajane</war:token>
         <war:id>1</war:id>
      </war:getWarehouseRequest>
   </soapenv:Body>
</soapenv:Envelope>
~~~

##### Väljundandmete kirjeldus
Elemendid vastuses andmetüüpidega on järgnevad:
* `warehouse`
    - `id` - `integer`, küsitava lao unikaalne identifikaator, genereeritakse süsteemi poolt;
    - `warehouseName` - `string`, küsitava lao nimi;
    - `warehouseAddress` - `string`, küsitava lao aadress;
    - `warehouseCapacity` - `double`, küsitava lao ruumala;
    - `warehouseArea` - `double`, küsitava lao pindala;
    - `warehouseMaterialList` - `warehouseMaterialListType`, küsitava lao materjalide nimekiri. Kui laos pole materjale siis see on tühi, vastupidisel juhul tagastab materjale mis kuuluvad sellele laole.

Kui päring ebaõnnestub, tagastatakse `Error` seisundi mille kohta informatsioon asub [Error seisundite SOAP-is](#error-seisundid-soap).

###### Näidis SOAP vastus (response)
~~~xml
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
   <S:Body>
      <getWarehouseResponse xmlns="http://www.ttu.ee/idu0075/warehouse">
         <warehouse>
            <id>1</id>
            <warehouseName>Tallinn Ladu</warehouseName>
            <warehouseAddress>Kalmistu tee 26</warehouseAddress>
            <warehouseCapacity>500.0</warehouseCapacity>
            <warehouseArea>1000.0</warehouseArea>
            <warehouseMaterialList/>
         </warehouse>
      </getWarehouseResponse>
   </S:Body>
</S:Envelope>
~~~




***
#### getWarehouseList SOAP
Kõikide ladude küsimise operatsioon. Operatsioonil on olemas mittekohustuslik (`optional`) päringu parameeter. See parameeter toimib nagu küsimise sorteerija. Vastuseks tagastatakse kõik laod selle parameetri järgi.

##### Sisendandmete kirjeldus
Päringus on ainult üks väärtus:
* `hasRelatedMaterials` - `string`, küsimise sorteerija piiranguga (`restiction`). Ainus vastuvõetav väärtus on `jah` või `ei`. Pannes `jah` tagastab kõik laod millel on materjalid, `ei` - kõik tühjad laod. Kui jätta tühjaks siis sorteerimine ei toimu ning tagastatakse kõik laod.

###### Näidis SOAP päring (request)
~~~xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:war="http://www.ttu.ee/idu0075/warehouse">
   <soapenv:Header/>
   <soapenv:Body>
      <war:getWarehouseListRequest>
         <war:token>salajane</war:token>
         <!--Optional:-->
         <war:hasRelatedMaterials>ei</war:hasRelatedMaterials>
      </war:getWarehouseListRequest>
   </soapenv:Body>
</soapenv:Envelope>
~~~

##### Väljundandmete kirjeldus
Vastuseks on nimekiri salvestatud ladudest. Elemendid vastuses andmetüüpidega on järgnevad:
* `warehouses` - Nimekiri ladudest.
    - `warehouse` - üks ladu, langeb kokku [getWarehouse operatsiooni](#getwarehouse-soap) vastusega:
        + `id` - `integer`;
        + `warehouseName` - `string`;
        + `warehouseAddress` - `string`;
        + `warehouseCapacity` - `double`;
        + `warehouseArea` - `double`.

Kui päring ebaõnnestub, tagastatakse `Error` seisundi mille kohta informatsioon asub [Error seisundite SOAP-is](#error-seisundid-soap).

###### Näidis SOAP vastus (response)
~~~xml
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
   <S:Body>
      <getWarehouseListResponse xmlns="http://www.ttu.ee/idu0075/warehouse">
         <warehouses>
            <id>1</id>
            <warehouseName>Tallinn Ladu</warehouseName>
            <warehouseAddress>Kalmistu tee 26</warehouseAddress>
            <warehouseCapacity>500.0</warehouseCapacity>
            <warehouseArea>1000.0</warehouseArea>
            <warehouseMaterialList/>
         </warehouses>
         <warehouses>
            <id>2</id>
            <warehouseName>Laki Ladu</warehouseName>
            <warehouseAddress>Suur-Sõjamäe 33a</warehouseAddress>
            <warehouseCapacity>615.12</warehouseCapacity>
            <warehouseArea>3333.34</warehouseArea>
            <warehouseMaterialList/>
         </warehouses>
      </getWarehouseListResponse>
   </S:Body>
</S:Envelope>
~~~




***
#### addMaterial SOAP
Materjali lisamise operatsioon. Saab lisada materjali millel on nimi ja kood, selle koostis ja tugevus.

##### Sisendandmete kirjeldus
Elemendid päringus andmetüüpidega on järgnevad:
* `material`
    - `name` - `string`, materjali nimi;
    - `code` - `string`, materjali kood. Kood genereeritakse loomisel tehases ja *võib!* sisaldada informatsiooni;
    - `composition` - `string`, materjali koostis (näiteks, aken koosneb klaasist ja plastikust);
    - `durability` - `string`, materjali tugevus (näiteks, akna koostises on klaas seega tugeves - nõrk).

Kui päring ebaõnnestub, tagastatakse `Error` seisundi mille kohta informatsioon asub [Error seisundite SOAP-is](#error-seisundid-soap).

###### Näidis SOAP päring (request)
~~~xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:war="http://www.ttu.ee/idu0075/warehouse">
   <soapenv:Header/>
   <soapenv:Body>
      <war:addMaterialRequest>
         <war:token>salajane</war:token>
         <war:name>Window</war:name>
         <war:code>WIN0001TLN20LDU</war:code>
         <war:composition>Glass, plastic</war:composition>
         <war:durability>Low</war:durability>
      </war:addMaterialRequest>
   </soapenv:Body>
</soapenv:Envelope>
~~~

##### Väljundandmete kirjeldus
Õnnestunud päringu vastus ehk päringu `Success` seisund. Elemendid vastuses andmetüüpidega on järgnevad:
* `material`
    - `id` - `integer`, loodud materjali unikaalne identifikaator, genereeritakse süsteemi poolt;
    - `name` - `string`, loodud materjali nimi;
    - `code` - `string`, loodud materjali kood;
    - `composition` - `string`, loodud materjali koostis;
    - `durability` - `string`, loodud materjali tugevus.
* `state` - `stateType`, päringu seisund.
    - `success` - `successType`, - õnnestunud päringu seisundi element.
        + `code` - `integer`, õnnestunud päringu seisundi kood. `200` tähendab, et materjal on edukalt loodud;
        + `message` - `string`, lisainformatsioon operatsiooni seisundi kohta.

Kui päring ebaõnnestub, tagastatakse `Error` seisundi mille kohta informatsioon asub [Error seisundite SOAP-is](#error-seisundid-soap).

Oluline ka see, et topelt saadetud päringud tagastavad juba loodud materjalit. 

###### Näidis SOAP vastus (response)
~~~xml
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
   <S:Body>
      <addMaterialResponse xmlns="http://www.ttu.ee/idu0075/warehouse">
         <material>
            <id>1</id>
            <name>Window</name>
            <code>WIN0001TLN20LDU</code>
            <composition>Glass, plastic</composition>
            <durability>Low</durability>
         </material>
         <state>
            <success>
               <code>200</code>
               <message>OK. Material successfully added!</message>
            </success>
         </state>
      </addMaterialResponse>
   </S:Body>
</S:Envelope>
~~~




***
#### getMaterial SOAP
Materjali küsimise operatsioon. Materjali saab otsida selle unikaalse identifikaatori ehk `id` järgi.

##### Sisendandmete kirjeldus
Päringus on ainult üks väärtus:
* `id` - `integer`, küsitava materjali id.

###### Näidis SOAP päring (request)
~~~xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:war="http://www.ttu.ee/idu0075/warehouse">
   <soapenv:Header/>
   <soapenv:Body>
      <war:getMaterialRequest>
         <war:token>salajane</war:token>
         <war:id>1</war:id>
      </war:getMaterialRequest>
   </soapenv:Body>
</soapenv:Envelope>
~~~

##### Väljundandmete kirjeldus
Elemendid vastuses andmetüüpidega on järgnevad:
* `material`
    - `id` - `integer`, küsitava materjali unikaalne identifikaator, genereeritakse süsteemi poolt;
    - `name` - `string`, küsitava materjali nimi;
    - `code` - `string`, küsitava materjali kood;
    - `composition` - `string`, küsitava materjali koostis;
    - `durability` - `string`, küsitava materjali tugevus.

Kui päring ebaõnnestub, tagastatakse `Error` seisundi mille kohta informatsioon asub [Error seisundite SOAP-is](#error-seisundid-soap).

###### Näidis SOAP vastus (response)
~~~xml
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
   <S:Body>
      <getMaterialResponse xmlns="http://www.ttu.ee/idu0075/warehouse">
         <material>
            <id>1</id>
            <name>Window</name>
            <code>WIN0001TLN20LDU</code>
            <composition>Glass, plastic</composition>
            <durability>Low</durability>
         </material>
      </getMaterialResponse>
   </S:Body>
</S:Envelope>
~~~




***
#### getMaterialList SOAP
Kõikide materjalide küsimise operatsioon. Vastuseks tagastatakse kõik materjalid.

##### Sisendandmete kirjeldus
Päringus peale `token`-i väärtuseid ei ole.

###### Näidis SOAP päring (request)
~~~xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:war="http://www.ttu.ee/idu0075/warehouse">
   <soapenv:Header/>
   <soapenv:Body>
      <war:getMaterialListRequest>
         <war:token>salajane</war:token>
      </war:getMaterialListRequest>
   </soapenv:Body>
</soapenv:Envelope>
~~~

##### Väljundandmete kirjeldus
Vastuseks on nimekiri salvestatud materjalidest. Elemendid vastuses andmetüüpidega on järgnevad:
* `materials` - nimekiri materjalidest.
    - `material` - Üks materjal, langeb kokku [getMaterial operatsiooni](#getmaterial-soap) vastusega:
        + `id` - `integer`;
        + `code` - `string`;
        + `name` - `string`;
        + `composition` - `string`;
        + `durability` - `string`.

Kui päring ebaõnnestub, tagastatakse `Error` seisundi mille kohta informatsioon asub [Error seisundite SOAP-is](#error-seisundid-soap).

###### Näidis SOAP vastus (response)
~~~xml
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
   <S:Body>
      <getMaterialListResponse xmlns="http://www.ttu.ee/idu0075/warehouse">
         <materials>
            <id>1</id>
            <name>Window</name>
            <code>WIN0001TLN20LDU</code>
            <composition>Glass, plastic</composition>
            <durability>Low</durability>
         </materials>
         <materials>
            <id>2</id>
            <name>Door</name>
            <code>DOO0001LAK33LDU</code>
            <composition>Wood</composition>
            <durability>Medium</durability>
         </materials>
      </getMaterialListResponse>
   </S:Body>
</S:Envelope>
~~~




***
#### addWarehouseMaterial SOAP
Materjali ladule lisamise operatsioon. Saab lisada materjali ning määrata selle hind ja kogus antud laos.

##### Sisendandmete kirjeldus
Elemendid päringus andmetüüpidega on järgnevad:
* `warehouseId` - `Integer`, lao id;
* `materialId` - `Integer`, materjali id;
* `quantity` - `Integer`, materjali kogus antud laos (näiteks, 20 tk.);
* `unitPrice` - `Integer`, ühe materjali hind (näiteks, 33.33 €).

###### Näidis SOAP päring (request)
~~~xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:war="http://www.ttu.ee/idu0075/warehouse">
   <soapenv:Header/>
   <soapenv:Body>
      <war:addWarehouseMaterialRequest>
         <war:token>salajane</war:token>
         <war:warehouseId>1</war:warehouseId>
         <war:materialId>1</war:materialId>
         <war:quantity>20</war:quantity>
         <war:unitPrice>33.33</war:unitPrice>
      </war:addWarehouseMaterialRequest>
   </soapenv:Body>
</soapenv:Envelope>
~~~

##### Väljundandmete kirjeldus
Õnnestunud päringu vastus ehk päringu `Success` seisund. Elemendid vastuses andmetüüpidega on järgnevad:
* `warehouseMaterial`
    - `material` - materjal, langeb kokku [getMaterial operatsiooni](#getmaterial-soap) vastusega:
        + `id` - `integer`;
        + `name` - `string`;
        + `code` - `string`;
        + `composition` - `string`;
        + `durability` - `string`.
    - `quantity` - `integer`, lisatud materjali kogus;
    - `unitPrice` - `integer`, hind ühe lisatud materjali kohta.
* `state`- `stateType`, päringu seisund.
    - `success` - `successType`, õnnestunud päringu seisundi element.
        + `code` - `integer`, õnnestunud päringu seisundi kood. `200` tähendab, et materjal on edukalt lisatud ladule;
        + `message` - `string`, lisainformatsioon operatsiooni seisundi kohta.

Kui selle identifikaatoriga materjal on juba olemas siis tagastatakse `Update` seisundi mis on sarnane `Success` seisundiga ja toimub vana materjali ülekirjutamine.

Kui päring ebaõnnestub, tagastatakse `Error` seisundi mille kohta informatsioon asub [Error seisundite SOAP-is](#error-seisundid-soap).

###### Näidis SOAP vastus (response)
~~~xml
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
   <S:Body>
      <addWarehouseMaterialResponse xmlns="http://www.ttu.ee/idu0075/warehouse">
         <warehouseMaterial>
            <material>
               <id>1</id>
               <name>Window</name>
               <code>WIN0001TLN20LDU</code>
               <composition>Glass, plastic</composition>
               <durability>Low</durability>
            </material>
            <quantity>20.0</quantity>
            <unitPrice>33.33</unitPrice>
         </warehouseMaterial>
         <state>
            <success>
               <code>200</code>
               <message>OK. WarehouseMaterial successfully added!</message>
            </success>
         </state>
      </addWarehouseMaterialResponse>
   </S:Body>
</S:Envelope>
~~~




***
#### getWarehouseMaterialList SOAP
Määratud lao kõikide materjalide küsimise operatsioon. Vastuseks tagastatakse määratud lao kõik materjalid. Kui laos pole materjale siis see on tühi, vastupidisel juhul tagastab materjale mis kuuluvad sellele ladule.

##### Sisendandmete kirjeldus
Päringus on ainult üks väärtus:
* `warehouseId` - `integer`, küsitava lao id.

###### Näidis SOAP päring (request)
~~~xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:war="http://www.ttu.ee/idu0075/warehouse">
   <soapenv:Header/>
   <soapenv:Body>
      <war:getWarehouseMaterialListRequest>
         <war:token>salajane</war:token>
         <war:warehouseId>1</war:warehouseId>
      </war:getWarehouseMaterialListRequest>
   </soapenv:Body>
</soapenv:Envelope>
~~~

##### Väljundandmete kirjeldus
Vastuseks on nimekiri küsitava lao salvestatud materjalidest. Elemendid vastuses andmetüüpidega on järgnevad:
* `warehouseMaterials` - nimekiri küsitava lao materjalidest.
    - `warehouseMaterial`
        + `material` - Üks materjal, langeb kokku [getMaterial operatsiooni](#getmaterial-soap) vastusega:
            + `id` - `integer`;
            + `code` - `string`;
            + `name` - `string`;
            + `composition` - `string`;
            + `durability` - `string`.
        + `quantity` - `integer`, küsitava lao antud materjali kogus;
        + `unitPrice` - `integer`, küsitava lao hind ühe antud materjali kohta.

Kui päring ebaõnnestub, tagastatakse `Error` seisundi mille kohta informatsioon asub [Error seisundite SOAP-is](#error-seisundid-soap).

###### Näidis SOAP vastus (response)
~~~xml
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
   <S:Body>
      <getWarehouseMaterialListResponse xmlns="http://www.ttu.ee/idu0075/warehouse">
         <warehouseMaterials>
            <warehouseMaterial>
               <material>
                  <id>1</id>
                  <name>Window</name>
                  <code>WIN0001TLN20LDU</code>
                  <composition>Glass, plastic</composition>
                  <durability>Low</durability>
               </material>
               <quantity>20.0</quantity>
               <unitPrice>33.33</unitPrice>
            </warehouseMaterial>
            <warehouseMaterial>
               <material>
                  <id>2</id>
                  <name>Door</name>
                  <code>DOO0001LAK33LDU</code>
                  <composition>Wood</composition>
                  <durability>Medium</durability>
               </material>
               <quantity>20.0</quantity>
               <unitPrice>33.33</unitPrice>
            </warehouseMaterial>
         </warehouseMaterials>
      </getWarehouseMaterialListResponse>
   </S:Body>
</S:Envelope>
~~~





### Error seisundid SOAP
Operatsiooni ebaõnnestamisel tagastatakse `Error` seisundi mis kirjeldab ebaõnnestumise põhjust täpsemalt.

Elemendid (ebaõnnestunud päringu vastuses) andmetüüpidega on järgnevad:
* `error` - `errorType`, `stateType` laiendus.
    - `code` - `integer`, ebaõnnestunud päringu seisundi kood;
    - `message` - `string`, lisainformatsioon operatsiooni seisundi kohta mis kirjeldab ebaõnnestumise põhjust.

Näiteks, vale `token`-i sisestamisel tuleb selline vastus:
~~~xml
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
   <S:Body>
      <getMaterialResponse xmlns="http://www.ttu.ee/idu0075/warehouse">
         <error>
            <code>401</code>
            <message>Unauthorized. Token is wrong!</message>
         </error>
      </getMaterialResponse>
   </S:Body>
</S:Envelope>
~~~

Kõikide seisundi koodide kirjeldused asuvad [Seisundi koodide tähenduste](#seisundi-koodide-tähendused) peatükis.
***





## REST API kirjeldus
REST API on saadaval lingina `(hostname)/WarehouseWebApplication`.

### REST API Operatsioonid
Realiseeritud REST API teenus võimaldab täita järgmised operatsioonid:
* [addWarehouse REST](#addwarehouse-rest)
* [getWarehouse REST](#getwarehouse-rest)
* [getWarehouseList REST](#getwarehouselist-rest)
* [addMaterial REST](#addmaterial-rest)
* [getMaterial REST](#getmaterial-rest)
* [getMaterialList REST](#getmateriallist-rest)
* [addWarehouseMaterial REST](#addwarehousematerial-rest)
* [getWarehouseMaterialList REST](#getwarehousemateriallist-rest)

### REST API Operatsioonide kirjeldused
Iga päring peab sisaldama korrektne `token`. 
Token lisatakse URL-ile query parameetritena `?token=salajane`.





***
#### addWarehouse REST
Ladu lisamise operatsioon. Saab lisada lao millel on nimi ja aadress, selle ruumala ja pindala.

HTTP meetod: `POST`

Ressurss (URI): `/warehouses`

Näidis URL: `/WarehouseWebApplication/webresources/warehouses/?token=salajane`

##### Sisendandmete kirjeldus
Elemendid päringus andmetüüpidega on järgnevad:
* `warehouseName` - `string`, lao nimi;
* `warehouseAddress` - `string`, lao aadress;
* `warehouseCapacity` - `double`, valiidne lao ruumala (näiteks, 615.12 või 843 m<sup>3</sup>);
* `warehouseArea` - `double`, valiidne lao pindala (näiteks, 3333.334 või 1500 m<sup>2</sup>).

###### Näidis JSON päring kui POST meetod (request)
~~~json
{
   "warehouseName": "Tallinn Ladu",
   "warehouseAddress": "Kalmistu tee 26",
   "warehouseCapacity": 500,
   "warehouseArea": 1000,
   "warehouseMaterialList": {"warehouseMaterial": []}
}
~~~

##### Väljundandmete kirjeldus
Õnnestunud päringu vastus ehk päringu `Success` seisund. Elemendid vastuses andmetüüpidega on järgnevad:
* `warehouse`
    - `id` - `integer`, loodud lao unikaalne identifikaator, genereeritakse süsteemi poolt;
    - `warehouseName` - `string`, loodud lao nimi;
    - `warehouseAddress` - `string`, loodud lao aadress;
    - `warehouseCapacity` - `double`, loodud lao ruumala;
    - `warehouseArea` - `double`, loodud lao pindala;
    - `warehouseMaterialList` - `warehouseMaterialListType`, tühi materjalide nimekiri.
* `state` - `stateType`, päringu seisund.
    - `success` - `successType`, - õnnestunud päringu seisundi element;
        + `code` - `integer`, õnnestunud päringu seisundi kood. `200` tähendab, et ladu on edukalt loodud;
        + `message` - `string`, lisainformatsioon operatsiooni seisundi kohta.
    - `error` - `errorType`, null.

Kui päring ebaõnnestub, tagastatakse `Error` seisundi mille kohta informatsioon asub [Error seisundite REST-is](#error-seisundid-rest).

###### Näidis JSON vastus (response)
~~~json
{
   "warehouse":    {
      "id": 1,
      "warehouseName": "Tallinn Ladu",
      "warehouseAddress": "Kalmistu tee 26",
      "warehouseCapacity": 500,
      "warehouseArea": 1000,
      "warehouseMaterialList": {"warehouseMaterial": []}
   },
   "state":    {
      "success":       {
         "code": 200,
         "message": "OK. Warehouse successfully added!"
      },
      "error": null
   }
}
~~~




***
#### getWarehouse REST
Ladu küsimise operatsioon. Ladu saab otsida selle unikaalse identifikaatori ehk `id` järgi.

HTTP meetod: `GET`

Ressurss (URI): `/warehouses/{warehouseId}`, kus `{warehouseId}` on lao id.

Näidis URL: `/WarehouseWebApplication/webresources/warehouses/1?token=salajane`

##### Väljundandmete kirjeldus
Elemendid vastuses andmetüüpidega on järgnevad:
* `warehouse`
    - `id` - `integer`, küsitava lao unikaalne identifikaator, genereeritakse süsteemi poolt;
    - `warehouseName` - `string`, küsitava lao nimi;
    - `warehouseAddress` - `string`, küsitava lao aadress;
    - `warehouseCapacity` - `double`, küsitava lao ruumala;
    - `warehouseArea` - `double`, küsitava lao pindala;
    - `warehouseMaterialList` - `warehouseMaterialListType`, küsitava lao materjalide nimekiri. Kui laos pole materjale siis see on tühi, vastupidisel juhul tagastab materjale mis kuuluvad sellele ladule.
* `error` - `errorType`, null.

Kui päring ebaõnnestub, tagastatakse `Error` seisundi mille kohta informatsioon asub [Error seisundite REST-is](#error-seisundid-rest).

Oluline ka see, et topelt saadetud päringud tagastavad juba loodud ladu. 

###### Näidis JSON vastus (response)
~~~json
{
   "warehouse":    {
      "id": 1,
      "warehouseName": "Tallinn Ladu",
      "warehouseAddress": "Kalmistu tee 26",
      "warehouseCapacity": 500,
      "warehouseArea": 1000,
      "warehouseMaterialList": {"warehouseMaterial": []}
   },
   "error": null
}
~~~




***
#### getWarehouseList REST
Kõikide ladude küsimise operatsioon. Operatsioonil on olemas mittekohustuslik (`optional`) päringu parameeter. See parameeter toimib nagu küsimise sorteerija. Vastuseks tagastatakse kõik laod selle parameetri järgi.

HTTP meetod: `GET`

Ressurss (URI): `/warehouses`

Näidis URL: `/WarehouseWebApplication/webresources/warehouses/?token=salajane&has_materials=`, kus `has_materials` - `string`, küsimise sorteerija piiranguga (`restiction`). Ainus vastuvõetav väärtus on `jah` või `ei`. Pannes `jah` tagastab kõik laod millel on materjalid, `ei` - kõik tühjad laod. Kui jätta tühjaks siis sorteerimine ei toimu ning tagastatakse kõik laod.

##### Väljundandmete kirjeldus
Vastuseks on nimekiri salvestatud ladudest. Elemendid vastuses andmetüüpidega on järgnevad:
* `warehouses` - nimekiri ladudest,
    - `warehouse` Üks ladu, langeb kokku [getWarehouse operatsiooni](#getwarehouse-rest) vastusega:
        + `id` - `integer`;
        + `warehouseName` - `string`;
        + `warehouseAddress` - `string`;
        + `warehouseCapacity` - `double`;
        + `warehouseArea` - `double`;
        + `warehouseMaterialList` - `warehouseMaterialListType`.
* `error` - `errorType`, null.

Kui päring ebaõnnestub, tagastatakse `Error` seisundi mille kohta informatsioon asub [Error seisundite REST-is](#error-seisundid-rest).

###### Näidis JSON vastus (response)
~~~json
{
   "warehouses":    [
            {
         "id": 1,
         "warehouseName": "Tallinn Ladu",
         "warehouseAddress": "Kalmistu tee 26",
         "warehouseCapacity": 500,
         "warehouseArea": 1000,
         "warehouseMaterialList": {"warehouseMaterial":          [
                        {
               "material":                {
                  "id": 1,
                  "name": "Window",
                  "code": "WIN0001TLN20LDU",
                  "composition": "Glass, plastic",
                  "durability": "Low"
               },
               "quantity": 20,
               "unitPrice": 33.33
            },
                        {
               "material":                {
                  "id": 2,
                  "name": "Door",
                  "code": "DOO0001LAK33LDU",
                  "composition": "Wood",
                  "durability": "Medium"
               },
               "quantity": 11,
               "unitPrice": 59.99
            }
         ]}
      },
            {
         "id": 2,
         "warehouseName": "Laki Ladu",
         "warehouseAddress": "Suur-Sõjamäe 33a",
         "warehouseCapacity": 615.12,
         "warehouseArea": 3333.34,
         "warehouseMaterialList": {"warehouseMaterial": []}
      }
   ],
   "error": null
}
~~~




***
#### addMaterial REST
Materjali lisamise operatsioon. Saab lisada materjali millel on nimi ja kood, selle koostis ja tugevus.

HTTP meetod: `POST`

Ressurss (URI): `/materials`

Näidis URL: `/WarehouseWebApplication/webresources/materials/?token=salajane`

##### Sisendandmete kirjeldus
Elemendid päringus andmetüüpidega on järgnevad:
* `name` - `string`, materjali nimi;
* `code` - `string`, materjali kood. Kood genereeritakse loomisel tehases ja *võib!* sisaldada informatsiooni;
* `composition` - `string`, materjali koostis (näiteks, aken koosneb klaasist ja plastikust);
* `durability` - `string`, materjali tugevus (näiteks, akna koostises on klaas seega tugeves - nõrk).

###### Näidis JSON päring kui POST meetod (request)
~~~json
{
   "name": "Window",
   "code": "WIN0001TLN20LDU",
   "composition": "Glass, plastic",
   "durability": "Low"
}
~~~

##### Väljundandmete kirjeldus
Õnnestunud päringu vastus ehk päringu `Success` seisund. Elemendid vastuses andmetüüpidega on järgnevad:
* `material`
    - `id` - `integer`, loodud materjali unikaalne identifikaator, genereeritakse süsteemi poolt;
    - `name` - `string`, loodud materjali nimi;
    - `code` - `string`, loodud materjali kood;
    - `composition` - `string`, loodud materjali koostis;
    - `durability` - `string`, loodud materjali tugevus.
* `state` - `stateType`, päringu seisund.
    - `success` - `successType`, - õnnestunud päringu seisundi element;
        + `code` - `integer`, õnnestunud päringu seisundi kood. `200` tähendab, et materjal on edukalt loodud;
        + `message` - `string`, lisainformatsioon operatsiooni seisundi kohta.
    - `error` - `errorType`, null.

Kui päring ebaõnnestub, tagastatakse `Error` seisundi mille kohta informatsioon asub [Error seisundite REST-is](#error-seisundid-rest).

Oluline ka see, et topelt saadetud päringud tagastavad juba loodud materjalit. 

###### Näidis JSON vastus (response)
~~~json
{
   "material":    {
      "id": 1,
      "name": "Window",
      "code": "WIN0001TLN20LDU",
      "composition": "Glass, plastic",
      "durability": "Low"
   },
   "state":    {
      "success":       {
         "code": 200,
         "message": "OK. Material successfully added!"
      },
      "error": null
   }
}
~~~




***
#### getMaterial REST
Materjali küsimise operatsioon. Materjali saab otsida selle unikaalse identifikaatori ehk `id` järgi.

HTTP meetod: `GET`

Ressurss (URI): `/materials/{materialId}`, kus `{materialId}` on materjali id.

Näidis URL: `/WarehouseWebApplication/webresources/warehouses/1?token=salajane`

##### Väljundandmete kirjeldus
Elemendid vastuses andmetüüpidega on järgnevad:
* `material`
    - `id` - `integer`, küsitava materjali unikaalne identifikaator, genereeritakse süsteemi poolt;
    - `name` - `string`, küsitava materjali nimi;
    - `code` - `string`, küsitava materjali kood;
    - `composition` - `string`, küsitava materjali koostis;
    - `durability` - `string`, küsitava materjali tugevus.
* `error` - `errorType`, null.

Kui päring ebaõnnestub, tagastatakse `Error` seisundi mille kohta informatsioon asub [Error seisundite REST-is](#error-seisundid-rest).

###### Näidis JSON vastus (response)
~~~json
{
   "material":    {
      "id": 1,
      "name": "Window",
      "code": "WIN0001TLN20LDU",
      "composition": "Glass, plastic",
      "durability": "Low"
   },
   "error": null
}
~~~




***
#### getMaterialList REST
Kõikide materjalide küsimise operatsioon. Vastuseks tagastatakse kõik materjalid.

HTTP meetod: `GET`

Ressurss (URI): `/materials`

Näidis URL: `/WarehouseWebApplication/webresources/warehouses/?token=salajane`

##### Väljundandmete kirjeldus
Vastuseks on nimekiri salvestatud materjalidest. Elemendid vastuses andmetüüpidega on järgnevad:
* `materials` - nimekiri materjalidest,
    - `material` - Üks materjal, langeb kokku [getMaterial operatsiooni](#getmaterial-rest) vastusega:
        + `id` - `integer`;
        + `code` - `string`;
        + `name` - `string`;
        + `composition` - `string`;
        + `durability` - `string`.
* `error` - `errorType`, null.

Kui päring ebaõnnestub, tagastatakse `Error` seisundi mille kohta informatsioon asub [Error seisundite REST-is](#error-seisundid-rest).

###### Näidis JSON vastus (response)
~~~json
{
   "materials":    [
            {
         "id": 1,
         "name": "Window",
         "code": "WIN0001TLN20LDU",
         "composition": "Glass, plastic",
         "durability": "Low"
      },
            {
         "id": 2,
         "name": "Door",
         "code": "DOO0001LAK33LDU",
         "composition": "Wood",
         "durability": "Medium"
      }
   ],
   "error": null
}
~~~




***
#### addWarehouseMaterial REST
Materjali ladule lisamise operatsioon. Saab lisada materjali ning määrata selle hind ja kogus antud laos.

HTTP meetod: `POST`

Ressurss (URI): `/warehouses/{warehouseId}/add_material`, kus `{warehouseId}` on lao id.

Näidis URL: `/WarehouseWebApplication/webresources/warehouses/1/add_material/?token=salajane`

##### Sisendandmete kirjeldus
Elemendid päringus andmetüüpidega on järgnevad:
* `materialId` - `Integer`, materjali id;
* `quantity` - `Integer`, materjali kogus antud laos (näiteks, 20 tk.);
* `unitPrice` - `Integer`, ühe materjali hind (näiteks, 33.33 €).

###### Näidis JSON päring kui POST meetod (request)
~~~json
{
  "materialId": 1,
  "quantity": 20,
  "unitPrice": 33.33
}
~~~

##### Väljundandmete kirjeldus
Õnnestunud päringu vastus ehk päringu `Success` seisund. Elemendid vastuses andmetüüpidega on järgnevad:
* `warehouseMaterial`
    - `material` - Üks materjal, langeb kokku [getMaterial operatsiooni](#getmaterial-rest) vastusega:
        + `id` - `integer`;
        + `name` - `string`;
        + `code` - `string`;
        + `composition` - `string`;
        + `durability` - `string`.
    - `quantity` - `integer`, lisatud materjali kogus;
    - `unitPrice` - `integer`, hind ühe lisatud materjali kohta.
* `state`- `stateType`, päringu seisund.
    - `success` - `successType`, õnnestunud päringu seisundi element;
        + `code` - `integer`, õnnestunud päringu seisundi kood. `200` tähendab, et materjal on edukalt lisatud ladule;
        + `message` - `string`, lisainformatsioon operatsiooni seisundi kohta.
    - `error` - `errorType`, null;
    - `update` - `updateType`, null.

Kui selle identifikaatoriga materjal on juba olemas siis tagastatakse `Update` seisundi mis on sarnane `Success` seisundiga ja toimub vana materjali ülekirjutamine.

Kui päring ebaõnnestub, tagastatakse `Error` seisundi mille kohta informatsioon asub [Error seisundite REST-is](#error-seisundid-rest).

###### Näidis JSON vastus (response)
~~~json
{
   "warehouseMaterial":    {
      "material":       {
         "id": 1,
         "name": "Window",
         "code": "WIN0001TLN20LDU",
         "composition": "Glass, plastic",
         "durability": "Low"
      },
      "quantity": 20,
      "unitPrice": 33.33
   },
   "state":    {
      "success":       {
         "code": 200,
         "message": "OK. WarehouseMaterial successfully added!"
      },
      "error": null,
      "update": null
   }
}
~~~



***
#### getWarehouseMaterialList REST
Määratud lao kõikide materjalide küsimise operatsioon. Vastuseks tagastatakse määratud ladu kõik materjalid. Kui laos pole materjale siis see on tühi, vastupidisel juhul tagastab materjale mis kuuluvad sellele ladule.

HTTP meetod: `GET`

Ressurss (URI): `/warehouses/{warehouseId}/materials`, kus `{warehouseId}` on lao id.

Näidis URL: `/WarehouseWebApplication/webresources/warehouses/1/materials/?token=salajane`

##### Väljundandmete kirjeldus
Vastuseks on nimekiri küsitava lao salvestatud materjalidest. Elemendid vastuses andmetüüpidega on järgnevad:
* `warehouseMaterials` - nimekiri küsitava lao materjalidest.
    - `warehouseMaterial`
        + `material` - Üks materjal, langeb kokku [getMaterial operatsiooni](#getmaterial-rest) vastusega:
            + `id` - `integer`;
            + `code` - `string`;
            + `name` - `string`;
            + `composition` - `string`;
            + `durability` - `string`.
        + `quantity` - `integer`, küsitava lao antud materjali kogus;
        + `unitPrice` - `integer`, küsitava lao hind ühe antud materjali kohta.
* `error` - `errorType`, null.

Kui päring ebaõnnestub, tagastatakse `Error` seisundi mille kohta informatsioon asub [Error seisundite REST-is](#error-seisundid-rest).

###### Näidis JSON vastus (response)
~~~json
{
   "warehouseMaterials": {"warehouseMaterial":    [
            {
         "material":          {
            "id": 1,
            "name": "Window",
            "code": "WIN0001TLN20LDU",
            "composition": "Glass, plastic",
            "durability": "Low"
         },
         "quantity": 20,
         "unitPrice": 33.33
      },
            {
         "material":          {
            "id": 2,
            "name": "Door",
            "code": "DOO0001LAK33LDU",
            "composition": "Wood",
            "durability": "Medium"
         },
         "quantity": 11,
         "unitPrice": 59.99
      }
   ]},
   "error": null
}
~~~





### Error seisundid REST
Operatsiooni ebaõnnestamisel tagastatakse `Error` seisundi mis kirjeldab ebaõnnestumise põhjust täpsemalt.

Elemendid (ebaõnnestunud päringu vastuses) andmetüüpidega on järgnevad:
* `error` - `errorType`, `stateType` laiendus.
    - `code` - `integer`, ebaõnnestunud päringu seisundi kood;
    - `message` - `string`, lisainformatsioon operatsiooni seisundi kohta mis kirjeldab ebaõnnestumise põhjust.

Näiteks, vale `token`-i sisestamisel tuleb selline vastus:
~~~json
{
   "error":    {
      "code": 401,
      "message": "Unauthorized. Token is wrong!"
   }
}
~~~

Kõikide seisundi koodide kirjeldused asuvad [Seisundi koodide tähenduste](#seisundi-koodide-tähendused) peatükis.





## Seisundi koodide tähendused

* `200` - õnnestunud päringu seisundi kood;
  - tähendab, et ladu on edukalt loodud;
  - tähendab, et ladu sellega nimega on juba loodud;
  - tähendab, et materjal on edukalt loodud;
  - tähendab, et materjal sellega nimega ja/või koodiga on juba loodud;
  - tähendab, et materjal on edukalt lisatud ladule;
  - tähendab, et materjal on edukalt uuendatud.

* `401` - vale `token`;

* `400` - kehtetu sisenditüüp või element puudub;

* `405` - meetod ei ole lubatud;
  - tähendab, et ladu nimekiri on tühi;
  - tähendab, et materjali nimekiri on tühi.

* `404` - ei leitud;
  - tähendab, et ladu pole leitud;
  - tähendab, et materjal pole leitud.

* `406` - pole vastuvõetav;
  - ainult vastuvõetavad väärtused on "jah", "ei" või jätta see väli tühjaks.

## Viited
Projekt aines Veebiteenused. Joosep Alviste. [WWW] [https://github.com/JoosepAlviste/veebiteenused-projekt](https://github.com/JoosepAlviste/veebiteenused-projekt) (21.11.2017)
Veebiteenused. Tarvo Treier. [WWW] [http://www.tud.ttu.ee/im/Tarvo.Treier/idu0075/2017/](http://www.tud.ttu.ee/im/Tarvo.Treier/idu0075/2017/) (21.11.2017)
