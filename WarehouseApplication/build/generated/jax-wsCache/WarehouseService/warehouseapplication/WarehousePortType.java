
package warehouseapplication;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebService(name = "WarehousePortType", targetNamespace = "http://www.ttu.ee/idu0075/warehouse")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface WarehousePortType {


    /**
     * 
     * @param parameter
     * @return
     *     returns warehouseapplication.GetMaterialResponse
     */
    @WebMethod
    @WebResult(name = "getMaterialResponse", targetNamespace = "http://www.ttu.ee/idu0075/warehouse", partName = "parameter")
    public GetMaterialResponse getMaterial(
        @WebParam(name = "getMaterialRequest", targetNamespace = "http://www.ttu.ee/idu0075/warehouse", partName = "parameter")
        GetMaterialRequest parameter);

    /**
     * 
     * @param parameter
     * @return
     *     returns warehouseapplication.AddMaterialResponse
     */
    @WebMethod
    @WebResult(name = "addMaterialResponse", targetNamespace = "http://www.ttu.ee/idu0075/warehouse", partName = "parameter")
    public AddMaterialResponse addMaterial(
        @WebParam(name = "addMaterialRequest", targetNamespace = "http://www.ttu.ee/idu0075/warehouse", partName = "parameter")
        AddMaterialRequest parameter);

    /**
     * 
     * @param parameter
     * @return
     *     returns warehouseapplication.GetMaterialListResponse
     */
    @WebMethod
    @WebResult(name = "getMaterialListResponse", targetNamespace = "http://www.ttu.ee/idu0075/warehouse", partName = "parameter")
    public GetMaterialListResponse getMaterialList(
        @WebParam(name = "getMaterialListRequest", targetNamespace = "http://www.ttu.ee/idu0075/warehouse", partName = "parameter")
        GetMaterialListRequest parameter);

    /**
     * 
     * @param parameter
     * @return
     *     returns warehouseapplication.GetWarehouseResponse
     */
    @WebMethod
    @WebResult(name = "getWarehouseResponse", targetNamespace = "http://www.ttu.ee/idu0075/warehouse", partName = "parameter")
    public GetWarehouseResponse getWarehouse(
        @WebParam(name = "getWarehouseRequest", targetNamespace = "http://www.ttu.ee/idu0075/warehouse", partName = "parameter")
        GetWarehouseRequest parameter);

    /**
     * 
     * @param parameter
     * @return
     *     returns warehouseapplication.AddWarehouseResponse
     */
    @WebMethod
    @WebResult(name = "addWarehouseResponse", targetNamespace = "http://www.ttu.ee/idu0075/warehouse", partName = "parameter")
    public AddWarehouseResponse addWarehouse(
        @WebParam(name = "addWarehouseRequest", targetNamespace = "http://www.ttu.ee/idu0075/warehouse", partName = "parameter")
        AddWarehouseRequest parameter);

    /**
     * 
     * @param parameter
     * @return
     *     returns warehouseapplication.GetWarehouseListResponse
     */
    @WebMethod
    @WebResult(name = "getWarehouseListResponse", targetNamespace = "http://www.ttu.ee/idu0075/warehouse", partName = "parameter")
    public GetWarehouseListResponse getWarehouseList(
        @WebParam(name = "getWarehouseListRequest", targetNamespace = "http://www.ttu.ee/idu0075/warehouse", partName = "parameter")
        GetWarehouseListRequest parameter);

    /**
     * 
     * @param parameter
     * @return
     *     returns warehouseapplication.GetWarehouseMaterialListResponse
     */
    @WebMethod
    @WebResult(name = "getWarehouseMaterialListResponse", targetNamespace = "http://www.ttu.ee/idu0075/warehouse", partName = "parameter")
    public GetWarehouseMaterialListResponse getWarehouseMaterialList(
        @WebParam(name = "getWarehouseMaterialListRequest", targetNamespace = "http://www.ttu.ee/idu0075/warehouse", partName = "parameter")
        GetWarehouseMaterialListRequest parameter);

    /**
     * 
     * @param parameter
     * @return
     *     returns warehouseapplication.AddWarehouseMaterialResponse
     */
    @WebMethod
    @WebResult(name = "addWarehouseMaterialResponse", targetNamespace = "http://www.ttu.ee/idu0075/warehouse", partName = "parameter")
    public AddWarehouseMaterialResponse addWarehouseMaterial(
        @WebParam(name = "addWarehouseMaterialRequest", targetNamespace = "http://www.ttu.ee/idu0075/warehouse", partName = "parameter")
        AddWarehouseMaterialRequest parameter);

}
