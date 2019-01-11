package app;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * Building a RESTful Web Service retrieved from: https://spring.io/guides/gs/rest-service/#scratch
 * https://spring.io/guides/gs/rest-service/#scratch
 * Additionally see: https://spring.io/guides/gs/accessing-mongodb-data-rest/
 */
@RestController
public class BikeOnTrackController {

    @RequestMapping("/getRestaurants")
    public String getRestaurants(@RequestParam(value="value") String value) {
        MongoConnector mongo = new MongoConnector();
        String res = mongo.getData("peiTrabalho", "SalesDetails", "StoreName", value);
        return res;
    }

    @RequestMapping("/getSales")
    public String getSales(@RequestParam(value="store")String store,@RequestParam(value="mes")int mes,@RequestParam(value="ano")int ano) {
        //Example: %5B%7B%24group%3A%7B%22_id%22%3Anull%2Ccount%3A%7B%24sum%3A1%7D%7D%7D%5D para [{$group:{"_id":null,count:{$sum:1}}}] -> utilizar: https://meyerweb.com/eric/tools/dencoder/
        MongoConnector mongo = new MongoConnector();
        String data=""+ano+"-0"+mes+"";
        //Cada Sale
        //Total de produtos vendidos
        String query1="[{$match:{StoreName: {$eq: '"+store+"'}}},{$group: {_id:\"$ReceiptID\",Total:{$sum:\"$Quantity\"}}},{$sort:{Total:1}}]";
        //Total de Produtos diferentes
        String query2="[{$match:{StoreName: {$eq: '"+store+"'}}},{$group:{_id:\"$ReceiptID\",Total:{$sum:1}}},{$sort:{Total:1}}]";
        //Media de preço dos produtos vendidos
        String query3="[{$match:{StoreName: {$eq: '"+store+"'}}},{$group: {_id:\"$ReceiptID\",MediaPrecoProduto:{$avg:\"$UnitPrice\"}}},{$sort:{MediaPrecoProduto:1}}]";

        //Cada Exercise
        //Total de Produtos Vendidos
        String query4="[{$match:{StoreName: {$eq: '"+store+"'},OrderDate:{$gte:'"+data+"-01 00:00:00.000',$lte:'"+data+"-31 00:00:00.000'}}},{$group: {_id:\"TotalProdutos\",Total:{$sum:\"$Quantity\"}}},{$sort:{Total:1}}]";
        //Total de Produtos Diferentes
        String query5="[{$match:{StoreName: {$eq: '"+store+"'},OrderDate:{$gte:'"+data+"-01 00:00:00.000',$lte:'"+data+"-31 00:00:00.000'}}},{$group:{_id:\"$ProductID\"}},{$group:{_id:\"TotalProdutosDiferentes\",Total:{$sum:1}}},{$sort:{Total:1}}]";
        //Total Clientes diferentes
        String query6="[{$match:{StoreName: {$eq: '"+store+"'},OrderDate:{$gte:'"+data+"-01 00:00:00.000',$lte:'"+data+"-31 00:00:00.000'}}}, {$group:{_id:\"$Customer\"}},{$group:{_id:\"NumerosClientesDiferentes\",Total:{$sum:1}}},{$sort:{Total:1}}]";
        //Valor vendido por Cliente
        String query7="[{$match:{StoreName: {$eq: '"+store+"'},OrderDate:{$gte:'"+data+"-01 00:00:00.000',$lte:'"+data+"-31 00:00:00.000'}}},{$group: {_id:\"$Customer\",TotalVendidoCliente:{$sum:\"$LineTotal\"}}},{$sort:{TotalVendidoCliente:-1}}]";
        //Total de Unidades vendidas por Produto
        String query8="[{$match:{StoreName: {$eq: '"+store+"'},OrderDate:{$gte:'"+data+"-01 00:00:00.000',$lte:'"+data+"-31 00:00:00.000'}}},{$group: {_id:\"$ProductID\",TotalProdutosVendidos:{$sum:\"$Quantity\"}}},{$sort:{TotalProdutosVendidos:-1}}]";
        //Valor vendido por moeda
        String query9="[{$match:{StoreName: {$eq: '"+store+"'},OrderDate:{$gte:'"+data+"-01 00:00:00.000',$lte:'"+data+"-31 00:00:00.000'}}},{$group: {_id:\"$CurrencyRateID\",TotalVendidoMoeda:{$sum:\"$LineTotal\"}}}]";

        //Valor por Loja
        //Valor Total de Produtos por Loja
        String query10="[{$match:{StoreName: {$eq: '"+store+"'}}},{$group: {_id:\"$ReceiptID\",TotalProdutos:{$sum:\"$Quantity\"}}},{$sort:{TotalProdutos:1}}]";
        //Total de Vendas Por Loja
        String query11="[{$match:{StoreName: {$eq: '"+store+"'}}},{$group: {_id:\"$ReceiptID\",Total:{$sum:\"$LineTotal\"}}},{$group:{_id:VendasTotais, Resultado:{$sum:\"$Total\"}}},{$sort:{Resultado:1}}]";
        //Valor Médio do Preço dos Produtos por Loja
        String query12="[{$match:{StoreName: {$eq: '"+store+"'}}},{$group: {_id:\"$StoreName\",ValorMedioProduto:{$avg:\"$UnitPrice\"}}}]";

        String print = "1-Total de Produtos de Venda: "+mongo.aggregateDataByQueryString("peiTrabalho", "SalesDetails", query1)+"\n";
        print +="2-Total de Produtos diferentes de Venda: "+mongo.aggregateDataByQueryString("peiTrabalho", "SalesDetails", query2)+"\n";
        print +="3-Media de preço do produto por venda: "+mongo.aggregateDataByQueryString("peiTrabalho", "SalesDetails", query3)+"\n";
        print +="4-Total de Produtos Por Exercicio: "+mongo.aggregateDataByQueryString("peiTrabalho", "SalesDetails", query4)+"\n";
        print +="5-Total de Produtos Diferentes Por Exercicio:"+mongo.aggregateDataByQueryString("peiTrabalho", "SalesDetails", query5)+"\n";
        print +="6-Total de Clientes Diferentes Por Exercicio:"+mongo.aggregateDataByQueryString("peiTrabalho", "SalesDetails", query6)+"\n";
        print +="7-Valor Vendido por Cliente Por Exercicio:"+mongo.aggregateDataByQueryString("peiTrabalho", "SalesDetails", query7)+"\n";
        print +="8-Total de Produtos vendidos Por Exercicio:"+mongo.aggregateDataByQueryString("peiTrabalho", "SalesDetails", query8)+"\n";
        print +="9-Valor Vendido Por Moeda Por Exercicio:"+mongo.aggregateDataByQueryString("peiTrabalho", "SalesDetails", query9)+"\n";
        print +="10-Valor Total de Produtos por Loja:"+mongo.aggregateDataByQueryString("peiTrabalho", "SalesDetails", query10)+"\n";
        print +="11-Total de Vendas Por Loja:"+mongo.aggregateDataByQueryString("peiTrabalho", "SalesDetails", query11)+"\n";
        print +="12-Valor Médio do Preço dos Produtos por Loja:"+mongo.aggregateDataByQueryString("peiTrabalho", "SalesDetails", query12)+"\n";
        return print;
    }
}