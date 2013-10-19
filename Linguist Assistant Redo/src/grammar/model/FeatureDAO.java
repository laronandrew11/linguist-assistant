package grammar.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import commons.dao.DAOFactory;
import commons.dao.DAOUtil;
import commons.dao.DBUtil;

public class FeatureDAO {
    
    /*
     * SQL Statements
     */
    private DAOFactory fDAOFactory;
    
    private static final String SQL_POSSIBLE_VALUES = 
            "SELECT FeatureValue.name AS name " +
            "  FROM FeatureValue " +
            "       JOIN Feature " +
            "         ON FeatureValue.featurePk = Feature.pk " +
            "       JOIN SemanticCategory " +
            "         ON Feature.semanticCategoryPk = SemanticCategory.pk " +
            "       JOIN SyntacticCategory " +
            "         ON SemanticCategory.syntacticCategoryPk = SyntacticCategory.pk " +
            " WHERE SyntacticCategory.name = (?) " +
            "       AND " +
            "       Feature.name = (?) ";
    
    
    public void main(String[] args) {
        Constituent cons = new Constituent(null);
        cons.setLabel("Noun");
        Feature feature = new Feature("type", cons);
    }
    
    public FeatureDAO(DAOFactory aDAOFactory) {
        fDAOFactory = aDAOFactory;
    }

    List<String> getPossibleValues(Feature aFeature) {
        ArrayList<String> possibleValues = new ArrayList<>();
        Object[] values = {
                aFeature.getParent().getSyntacticCategory(),
                aFeature.getName()
        };
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String sql = SQL_POSSIBLE_VALUES;
            conn = fDAOFactory.getConnection();
            ps = DAOUtil.prepareStatement(conn, sql, false, values);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                possibleValues.add(rs.getString("name"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DAOUtil.close(conn, ps, rs);
        }
        return possibleValues;
    }
}