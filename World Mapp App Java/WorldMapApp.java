import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.sql.*;

public class WorldMapApp extends JFrame {
    private JPanel mapPanel;
    private JLabel infoLabel;

    public WorldMapApp(){
        setTitle("Παγκόσμιος Χάρτης");
        setSize(800,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        mapPanel=new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                //Φόρτωση Εικόνας Χάρτη
                ImageIcon mapImage=new ImageIcon("img/world_map.jpg");//path της εικόνας
                //Σχεδίαση της εικόνας στο mapPanel
                g.drawImage(mapImage.getImage(),0,0,getWidth(),getHeight(),null);
            }//end of paintComponent
        };//end of mapPanel

        //Προσθήκη MouseListener
        mapPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Λήψη των συντεταγμένων
                int x = e.getX();
                int y = e.getY();
                //Διαχείριση του x,y με το κλικ
                handleMapClick(x, y);
            }
        });

        // Πρόσθεση MouseMotionListener για hover
        mapPanel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Δεν χρειάζεται να κάνουμε κάτι όταν ο χρήστης μεταφέρει το ποντίκι με το κουμπί πατημένο
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                // Λήψη των συντεταγμένων του ποντικιού
                int x = e.getX();
                int y = e.getY();
                // Ενημέρωση της infoLabel με τις συντεταγμένες
                infoLabel.setText("Συντεταγμένες: (" + x + ", " + y + ")");
            }
        });

        //Δημιουργία ετικέτας για εμφάνιση πληροφοριών
        infoLabel=new JLabel("Κάντε κλικ για πληροφορίες");
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //Προσθήκη πληροφοριών
        add(mapPanel,BorderLayout.CENTER);
        add(infoLabel,BorderLayout.SOUTH);
        setVisible(true);

    }//end of constructor
    private void handleMapClick(int x,int y){
    String countryInfo=getCountryInfo(x,y);
    if (countryInfo !=null){
        infoLabel.setText(countryInfo);
        }
    else
        {
        infoLabel.setText("Δεν βρέθηκαν πληροφορίες");
        }
    }

    private String getCountryInfo(int x,int y){
        String url="jdbc:mysql://localhost:port/dbName";
        String user="user";
        String pw="mysecretpw";

        try(Connection conn = DriverManager.getConnection(url,user,pw)){
            String query="SELECT name, capital, population, continent, coordinates " +
                    "FROM countries " +
                    "where coordinates= ?";
            try(PreparedStatement stmt=conn.prepareStatement(query)){
                stmt.setString(1,x+","+y);
                ResultSet rs=stmt.executeQuery();
                if(rs.next()){
                    String name=rs.getString("name");
                    String capital=rs.getString("capital");
                    long population=rs.getLong("population");
                    String continent=rs.getString("continent");

                    return String.format("Χώρα: %s , Πρωτεύουσα: %s, Πληθυσμός: %d, Ήπειρος: %s",name,capital,population,continent);

                }

            } catch (SQLException err) {
                err.printStackTrace();
                JOptionPane.showMessageDialog(this,"Retrieve Data failed","Error",JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException err) {
            err.printStackTrace();
            JOptionPane.showMessageDialog(this,"DB Connection failed","Error",JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
    public static void main(String[] args) {
        WorldMapApp myWMapp=new WorldMapApp();
    }
}//end of class
