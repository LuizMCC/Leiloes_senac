/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){
        
        conn = new conectaDAO().connectDB();
        
        try{
            prep = conn.prepareStatement("insert into produtos(nome, valor, status) values(?,?,?)");
            prep.setString(1, produto.getNome());
            prep.setString(2, String.valueOf(produto.getValor()));
            prep.setString(3, produto.getStatus());
            
            int status = prep.executeUpdate();
            
            if(status == 1){
                JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso");
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto\ncodigo do erro: " + status);
            }
            
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + e.getErrorCode());
        }
        
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        conn = new conectaDAO().connectDB();
        listagem = new ArrayList<>();
        try{
            prep = conn.prepareStatement("select * from produtos");
            resultset = prep.executeQuery();
            
            while(resultset.next()){
                ProdutosDTO p = new ProdutosDTO();
                p.setId(resultset.getInt("id"));
                p.setNome(resultset.getString("nome"));
                p.setValor(resultset.getInt("valor"));
                p.setStatus(resultset.getString("status"));
                
                listagem.add(p);
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao listar produto: " + e.getErrorCode());
        }
        return listagem;
    }
    
    public ArrayList<ProdutosDTO> listarProdutosVendidos(){
        conn = new conectaDAO().connectDB();
        listagem = new ArrayList<>();
        try{
            prep = conn.prepareStatement("select * from produtos where status = Vendido");
            resultset = prep.executeQuery();
            
            while(resultset.next()){
                ProdutosDTO p = new ProdutosDTO();
                p.setId(resultset.getInt("id"));
                p.setNome(resultset.getString("nome"));
                p.setValor(resultset.getInt("valor"));
                p.setStatus(resultset.getString("status"));
                
                listagem.add(p);
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos vendidos: " + e.getErrorCode());
        }
        return listagem;
    }
    
    public void venderProduto(int ID){
        conn = new conectaDAO().connectDB();
        try{
            prep = conn.prepareStatement("update produtos set status = 'Vendido' where id = ?");
            prep.setString(1, String.valueOf(ID));
            
            int status = prep.executeUpdate();
            
            if(status == 1){
                JOptionPane.showMessageDialog(null, "Produto vendido com sucesso");
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao vender produto\ncodigo do erro: " + status);
            }
            
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + e.getErrorCode());
        }
    }
    
    public ProdutosDTO pegarProduto(int ID){
        conn = new conectaDAO().connectDB();
        ProdutosDTO produto = new ProdutosDTO();
        try{
            prep = conn.prepareStatement("select * from produtos where id = ?");
            prep.setString(1, String.valueOf(ID));
            
            resultset = prep.executeQuery();
            resultset.next();
            
            produto.setId(resultset.getInt("id"));
            produto.setNome(resultset.getString("nome"));
            produto.setStatus(resultset.getString("status"));
            produto.setValor(resultset.getInt("valor"));
            
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao pegar produto: " + e.getErrorCode());
        }
        
        return produto;
    }
}

