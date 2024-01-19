package com.example.stocks_portfolio.service.impl;

import com.example.stocks_portfolio.DTO.StockDTO;
import com.example.stocks_portfolio.entities.Stock;
import com.example.stocks_portfolio.exceptions.DataNotFoundException;
import com.example.stocks_portfolio.repositories.StockRepository;
import com.example.stocks_portfolio.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.ParseException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    @Override
    public StockDTO getStockById(String id) {
        Optional<Stock> stock = stockRepository.findById(id);

        System.out.println(stock.isPresent());

        if(!stock.isPresent()){
            throw new DataNotFoundException("stock with id = " + id + "does not exist");
        }

        return convertToStockDTO(stock.get());
    }

    @Override
    public String updateStocksFromFile(MultipartFile fileName) throws Exception {
        InputStream is = fileName.getInputStream();
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(is));
        return updateStocksinDB(inputReader);
    }

    private String updateStocksinDB(BufferedReader inputReader) throws IOException, ParseException {
        String line = null;
        boolean firstLine = true;

        while((line = inputReader.readLine()) != null){
            if(firstLine){
                firstLine = false;
                continue;
            }

            String[] columns = line.split(",");
            String stock_id = columns[12];
            String stock_name = columns[0];
            double open_price = Double.parseDouble(columns[2]);
            double high_price = Double.parseDouble(columns[3]);
            double low_price = Double.parseDouble(columns[4]);
            double close_price = Double.parseDouble(columns[5]);

            Stock stock = new Stock(stock_id, stock_name, open_price, close_price, high_price, low_price);

            stockRepository.save(stock);
        }

        return "Saved successfully";
    }

    private StockDTO convertToStockDTO(Stock stock) {
        StockDTO stockDTO = new StockDTO();

        stockDTO.setStockId(stock.getStockId());
        stockDTO.setStockName(stock.getStockName());
        stockDTO.setClosePrice(stock.getClosePrice());
        stockDTO.setLowPrice(stock.getLowPrice());
        stockDTO.setOpenPrice(stock.getOpenPrice());
//        stockDTO.setSettlement_price(stock.getOpen_price());
        stockDTO.setHighPrice(stock.getHighPrice());

        return stockDTO;
    }
}
