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
    
    
    
        
}

