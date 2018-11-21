package vue_et_controlleur.login;

import java.io.File;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import serialisationEtdeserialisation.FirstSerializerDocument;
import serialisationEtdeserialisation.LectureDesFichiers;

public class Login extends Application {
	
	private String strModele = "./modele/", 
			strVueEtControlleur = "./vue_et_controlleur/";
	
	private Stage stage;
	
	private boolean blnProfPasDeStyle = false;
	
	private GridPane gridPane;
	
	private RadioButton rbPrepose, rbAdherent;
	
	private Label lblPreposeNoUser, lblPreposePwd;
	private Label lblAdherentNoTel, lblAdherentOu, lblAdherentNom, lblAdherentPrenom;
	private Label[] arrLbl;
	
	private TextField textFieldPreposeNoUser;
	private PasswordField pwdFieldPreposePwd;
	private TextField textFieldAdherentNoTel, textFieldAdherentNom, textFieldAdherentPrenom;
	private TextField[] arrTextField;
	
	private Label lblMsgErreur;
	
	private Button btnConnexion, btnEnregistrement;

	@Override
	public void start(Stage primaryStage) throws Exception {
		serialisation();
		
		this.stage = primaryStage;
		
		BorderPane root = new BorderPane();
		
		Scene scene = new Scene(root, 503, 384);
		
		HBox hBoxTop = new HBox(10);
		
		Label lblTop = new Label("Je suis un :");
		
		lblTop.setAlignment(Pos.CENTER_RIGHT);
		lblTop.setTextAlignment(TextAlignment.RIGHT);
		lblTop.setTextFill(Color.WHITE);
		lblTop.setFont(font(20, null));
		
		ToggleGroup tGroup = new ToggleGroup();
		rbPrepose = new RadioButton("P�pos�");
		rbAdherent = new RadioButton("Adh�rent");
		
		rbPrepose.setAlignment(Pos.CENTER);
		rbAdherent.setAlignment(Pos.CENTER);
		
		rbPrepose.setToggleGroup(tGroup);
		rbAdherent.setToggleGroup(tGroup);
		
		rbPrepose.setTextFill(Color.WHITE);
		rbAdherent.setTextFill(Color.WHITE);
		
		rbPrepose.setFont(font(20, FontWeight.BOLD));
		rbAdherent.setFont(font(20, FontWeight.BOLD));
		
		rbPrepose.setOnAction(new GestionConnexion());
		rbAdherent.setOnAction(new GestionConnexion());
		
		hBoxTop.setAlignment(Pos.CENTER);
		hBoxTop.getChildren().addAll(rbPrepose, rbAdherent);
		
		//Adding GridPaneCenter
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        //gridPane.setPadding(new Insets(20,20,20,20));
        gridPane.setHgap(5);
        gridPane.setVgap(10);
        
        /*Text text = new Text("Veillez vous identifier");
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFill(Color.WHITE);
        text.setFont(font(40, FontWeight.BOLD));*/
        
        //Implementing Nodes for GridPane
        lblPreposeNoUser = new Label("No. de l'employ� :");
        lblPreposePwd = new Label("Mot de passe :");
        textFieldPreposeNoUser = new TextField();
        pwdFieldPreposePwd = new PasswordField();
        
        lblAdherentNoTel = new Label("No. de t�l�phone :");
        lblAdherentOu = new Label("OU");
        lblAdherentNom = new Label("Nom :");
        lblAdherentPrenom = new Label("Pr�nom :");
        textFieldAdherentNoTel = new TextField();
        textFieldAdherentNom = new TextField();
        textFieldAdherentPrenom = new TextField();
        
        arrLbl = new Label[] {lblPreposeNoUser, lblPreposePwd, lblAdherentNoTel, lblAdherentOu, lblAdherentNom, lblAdherentPrenom};
        arrTextField = new TextField[] {textFieldPreposeNoUser, textFieldAdherentNoTel, textFieldAdherentNom, textFieldAdherentPrenom};
        
        btnConnexion = new Button("Connexion");
        btnEnregistrement = new Button("Inscription");
        
        lblMsgErreur = new Label("");
        
        for (Label lbl : arrLbl) {
        	lbl.setTextFill(Color.WHITE);
        	lbl.setTextAlignment(TextAlignment.RIGHT);
        	lbl.setAlignment(Pos.CENTER_RIGHT);
        	lbl.setFont(font(20, null));
        }
        lblTop.setFont(font(25, FontWeight.BOLD));
        lblMsgErreur.setTextFill(Color.YELLOW);
        lblMsgErreur.setTextAlignment(TextAlignment.CENTER);
        lblMsgErreur.setAlignment(Pos.CENTER);
        lblMsgErreur.setFont(font(15, FontWeight.BOLD));
        
        for (TextField textField : arrTextField) {
        	textField.setFont(font(15, null));
        	textField.setOnKeyPressed(new GestionClavier());
        }
        pwdFieldPreposePwd.setFont(font(15, null));
        pwdFieldPreposePwd.setOnKeyPressed(new GestionClavier());
        
        btnConnexion.setAlignment(Pos.CENTER_RIGHT);
        btnEnregistrement.setAlignment(Pos.CENTER_RIGHT);
        
        btnConnexion.setFont(font(15, FontWeight.BOLD));
        btnEnregistrement.setFont(font(15, FontWeight.BOLD));
        
        btnConnexion.setGraphic(
        		new ImageView(new Image(strModele + "secure-icon-lock-secure-icon-27.png", 20, 20, false, false)));
        
        btnConnexion.setOnAction(new GestionConnexion());
        btnEnregistrement.setOnAction(new GestionConnexion());
        
        if (blnProfPasDeStyle) {
	        // https://stackoverflow.com/questions/12717487/how-to-implement-a-transparent-pane-with-non-transparent-children
	        //textFieldPreposeNoUser.setFill(Color.WHITE);   
        	for (TextField textField : arrTextField) {
        		textField.setStyle("-fx-background-color: rgba(0, 100, 100, 0.5); /*-fx-background-radius: 10;*/ -fx-text-inner-color: white;");
        		textField.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID,  CornerRadii.EMPTY, new BorderWidths(1))));
        	}
	        /*textFieldPreposeNoUser.skinProperty().addListener(new ChangeListener<Skin<?>>() {
	
	            @Override
	            public void changed(
	              ObservableValue<? extends Skin<?>> ov, Skin<?> t, Skin<?> t1) {
	                if (t1 != null && t1.getNode() instanceof Region) {
	                    Region r = (Region) t1.getNode();
	                    r.setBackground(Background.EMPTY);
	
	                    r.getChildrenUnmodifiable().stream().
	                            filter(n -> n instanceof Region).
	                            map(n -> (Region) n).
	                            forEach(n -> n.setBackground(Background.EMPTY));
	
	                    r.getChildrenUnmodifiable().stream().
	                            filter(n -> n instanceof Control).
	                            map(n -> (Control) n).
	                            forEach(c -> c.skinProperty().addListener(this)); // *
	                }
	            }
	        });*/
	        
	        pwdFieldPreposePwd.setStyle("-fx-background-color: rgba(0, 100, 100, 0.5); /*-fx-background-radius: 10;*/ -fx-text-inner-color: white;");
	        pwdFieldPreposePwd.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID,  CornerRadii.EMPTY, new BorderWidths(1))));
	        /*pwdFieldPreposePwd.skinProperty().addListener(new ChangeListener<Skin<?>>() {
	
	            @Override
	            public void changed(
	              ObservableValue<? extends Skin<?>> ov, Skin<?> t, Skin<?> t1) {
	                if (t1 != null && t1.getNode() instanceof Region) {
	                    Region r = (Region) t1.getNode();
	                    r.setBackground(Background.EMPTY);
	
	                    r.getChildrenUnmodifiable().stream().
	                            filter(n -> n instanceof Region).
	                            map(n -> (Region) n).
	                            forEach(n -> n.setBackground(Background.EMPTY));
	
	                    r.getChildrenUnmodifiable().stream().
	                            filter(n -> n instanceof Control).
	                            map(n -> (Control) n).
	                            forEach(c -> c.skinProperty().addListener(this)); // *
	                }
	            }
	        });*/
        }
        
        //Adding Nodes to GridPane layout
        GridPane.setHalignment(lblTop, HPos.RIGHT);
        
        for (Label lbl : arrLbl) {
        	GridPane.setHalignment(lbl, HPos.RIGHT);
        }
        
        GridPane.setHalignment(lblAdherentOu, HPos.CENTER);
        
        GridPane.setHalignment(btnConnexion, HPos.RIGHT);
        GridPane.setHalignment(btnEnregistrement, HPos.RIGHT);
        
        GridPane.setHalignment(lblMsgErreur, HPos.CENTER);
        gridPane.add(lblTop, 0, 0);
        gridPane.add(hBoxTop, 1, 0);
        gridPane.add(lblPreposeNoUser, 0, 3);
        gridPane.add(textFieldPreposeNoUser, 1, 3);
        gridPane.add(lblPreposePwd, 0, 4);
        gridPane.add(pwdFieldPreposePwd, 1, 4);
        gridPane.add(btnConnexion, 1, 8);
        gridPane.add(btnEnregistrement, 1, 10);
        //gridPane.add(lblMsgErreur, 1, 15);
        
        //Add HBox and GridPane layout to BorderPane Layout
        
        //root.setAlignment(gridPane, Pos.CENTER);
        /*root.setMargin(text, new Insets(80, 0, 0, 0));
        root.setMargin(gridPane, new Insets(80, 0, 0, 0));*/
        //root.setPadding(new Insets(20));
        BorderPane.setAlignment(gridPane, Pos.CENTER);
        BorderPane.setAlignment(lblMsgErreur, Pos.CENTER);
        root.setCenter(gridPane);
        root.setBottom(lblMsgErreur);
        root.setBackground(new Background(
				new BackgroundImage(new Image(strModele + "page-ipad-ipad-ipad-mini-library-wallpapers-hd-desktop-1024768-library-clipart-background-1024_768.jpg"), 
												BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, 
												new BackgroundSize(scene.getWidth()+10, scene.getHeight()+10, false, false, false, false))));
        
        loginPrepose(false);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Bienvenue � la m�diath�que - Connexion");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	private void serialisation() {
		/*LectureDesFichiers.LireFichierAdh�rant(strModele + "Adh�rent.txt");
		LectureDesFichiers.LireFichierDVD(strModele + "DVD.txt");
		LectureDesFichiers.LireFichierLivres(strModele + "Livres.txt");
		LectureDesFichiers.LireFichierPeriodique(strModele + "Periodiques.txt");
		LectureDesFichiers.LireFichierPrepose(strModele + "Prepos�.txt");
		
		File fichier = new File(strModele + "DVD.ser");
		
		if(!fichier.exists()){
			FirstSerializerDocument.getInstance();
		}*/
	}
	
	private void shake(String strErreur, boolean blnPrepose) {
		retourSansErreur();
		
		if (blnPrepose) {
			lblPreposeNoUser.setTextFill(Color.YELLOW);
			lblPreposePwd.setTextFill(Color.YELLOW);
			
			if (blnProfPasDeStyle) {
				textFieldPreposeNoUser.setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID,  CornerRadii.EMPTY, new BorderWidths(1))));
				pwdFieldPreposePwd.setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID,  CornerRadii.EMPTY, new BorderWidths(1))));
			}
			
			textFieldPreposeNoUser.requestFocus();
		}
		else {
			lblAdherentNoTel.setTextFill(Color.YELLOW);
			lblAdherentNom.setTextFill(Color.YELLOW);
			lblAdherentPrenom.setTextFill(Color.YELLOW);
			
			if (blnProfPasDeStyle) {
				for (TextField textField : arrTextField) {
					if (textField != textFieldPreposeNoUser) {
						textField.setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID,  CornerRadii.EMPTY, new BorderWidths(1))));
					}
				}
			}
		}
		
		//lblMsgErreur.setText("No prepose ou mot de passe incorrecte");
		lblMsgErreur.setText(strErreur);
		lblMsgErreur.setTextFill(Color.YELLOW);

        // shake animation en cas d'erreur
        final int[] x = {0};
        final int[] y = {0};

        Timeline timelineX = new Timeline(new KeyFrame(Duration.seconds(0.1), t -> {
            if (x[0] == 0) {
            	stage.setX(stage.getX() + 10);
                x[0] = 1;
            } else {
            	stage.setX(stage.getX() - 10);
                x[0] = 0;
            }
        }));

        timelineX.setCycleCount(6);
        timelineX.setAutoReverse(false);
        timelineX.play();


        Timeline timelineY = new Timeline(new KeyFrame(Duration.seconds(0.1), t -> {
            if (y[0] == 0) {
            	stage.setY(stage.getY() + 10);
                y[0] = 1;
            } else {
            	stage.setY(stage.getY() - 10);
                y[0] = 0;
            }
        }));

        timelineY.setCycleCount(6);
        timelineY.setAutoReverse(false);
        timelineY.play();        
	}
	
	/*
	 * TODO afficher menu principal
	 */
	private void afficheMenuPrincipal() {
		
	}
	
	/*
	 * TODO check si les donn�es entr�es sont valides et sont dans les fichiers s�rialis�s
	 */
	private boolean connexion() {
		boolean blnConnexion = true;
		
		if (rbPrepose.isSelected()) {
			if (!textFieldPreposeNoUser.getText().isEmpty() && !pwdFieldPreposePwd.getText().isEmpty()) {
				if (!(textFieldPreposeNoUser.getText().equals("mhg") && pwdFieldPreposePwd.getText().equals("123"))) {
					blnConnexion = false;
					shake("No. de l'employ� et/ou mot de passe erron�", true);
				}
				
				pwdFieldPreposePwd.clear();
			}
			else {
				blnConnexion = false;
				shake("No. de l'employ� et/ou mot de passe vide", true);
			}
		}
		else if (rbAdherent.isSelected()) {
			if (!textFieldAdherentNoTel.getText().isEmpty() || (!textFieldAdherentNom.getText().isEmpty() && !textFieldAdherentPrenom.getText().isEmpty())) {
				if (!textFieldAdherentNoTel.getText().equals("123") || !(textFieldAdherentNom.getText().equals("m") && textFieldAdherentPrenom.getText().equals("h"))) {
					blnConnexion = false;
					shake("No. de t�l�phone ou nom et/ou pr�nom erron�", false);
				}
				
				pwdFieldPreposePwd.clear();
			}
			else {
				blnConnexion = false;
				shake("No. de t�l�phone ou nom et/ou pr�nom vide", false);
			}
		}
		
		return blnConnexion;
	}
	
	private void loginPrepose(boolean blnAffiche) {
		retourSansErreur();
		
		/*gridPane.add(lblTop, 0, 0);
        gridPane.add(hBoxTop, 1, 0);
        gridPane.add(lblPreposeNoUser, 0, 3);
        gridPane.add(textFieldPreposeNoUser, 1, 3);
        gridPane.add(lblPreposePwd, 0, 4);
        gridPane.add(pwdFieldPreposePwd, 1, 4);
        gridPane.add(btnConnexion, 1, 8);*/
		
		if (blnAffiche) {
			gridPane.add(lblPreposeNoUser, 0, 3);
	        gridPane.add(textFieldPreposeNoUser, 1, 3);
	        gridPane.add(lblPreposePwd, 0, 4);
	        gridPane.add(pwdFieldPreposePwd, 1, 4);
	        
	        gridPane.add(btnConnexion, 1, 8);
	        
	        gridPane.getChildren().remove(btnEnregistrement);
	        gridPane.add(btnEnregistrement, 1, 10);
		}
		else {
			gridPane.getChildren().remove(lblPreposeNoUser);
			gridPane.getChildren().remove(lblPreposePwd);
			gridPane.getChildren().remove(textFieldPreposeNoUser);
			gridPane.getChildren().remove(pwdFieldPreposePwd);
			
			gridPane.getChildren().remove(btnConnexion);
			
			gridPane.getChildren().remove(btnEnregistrement);
			gridPane.add(btnEnregistrement, 1, 4);
		}
	}
	private void loginAdherent(boolean blnAffiche) {
		retourSansErreur();
		
		/*gridPane.add(lblTop, 0, 0);
        gridPane.add(hBoxTop, 1, 0);
        gridPane.add(lblPreposeNoUser, 0, 3);
        gridPane.add(textFieldPreposeNoUser, 1, 3);
        gridPane.add(lblPreposePwd, 0, 4);
        gridPane.add(pwdFieldPreposePwd, 1, 4);
        gridPane.add(btnConnexion, 1, 8);*/
		
		if (blnAffiche) {
			gridPane.add(lblAdherentNoTel, 0, 3);
	        gridPane.add(lblAdherentNom, 0, 5);
	        gridPane.add(lblAdherentPrenom, 0, 6);
	        
	        gridPane.add(textFieldAdherentNoTel, 1, 3);
	        gridPane.add(lblAdherentOu, 1, 4);
	        gridPane.add(textFieldAdherentNom, 1, 5);
	        gridPane.add(textFieldAdherentPrenom, 1, 6);
	        
	        gridPane.add(btnConnexion, 1, 10);
	        
	        gridPane.getChildren().remove(btnEnregistrement);
	        gridPane.add(btnEnregistrement, 1, 12);
		}
		else {
			gridPane.getChildren().remove(lblAdherentNoTel);
			gridPane.getChildren().remove(lblAdherentNom);
			gridPane.getChildren().remove(lblAdherentPrenom);
			
			gridPane.getChildren().remove(textFieldAdherentNoTel);
			gridPane.getChildren().remove(lblAdherentOu);
			gridPane.getChildren().remove(textFieldAdherentNom);
			gridPane.getChildren().remove(textFieldAdherentPrenom);
			
			gridPane.getChildren().remove(btnConnexion);
			
			gridPane.getChildren().remove(btnEnregistrement);
			gridPane.add(btnEnregistrement, 1, 4);
		}
}
	
	private class GestionConnexion implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			if (event.getSource() == rbPrepose) {
				loginAdherent(false);
				loginPrepose(true);
			}
			else if (event.getSource() == rbAdherent) {
				loginPrepose(false);
				loginAdherent(true);
			}
			else if (event.getSource() == btnConnexion) {
				if (connexion()) {
					afficheMenuPrincipal();
					
					retourSansErreur();
					
					stage.close();
					
					
				}
			}
			else if (event.getSource() == btnEnregistrement) {
				retourSansErreur();
				
				stage.close();
				
				new Inscription(blnProfPasDeStyle, stage).show();
			}
		}
		
	}
	private class GestionClavier implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent event) {
			if (event.getEventType() == KeyEvent.KEY_PRESSED && event.getCode() == KeyCode.ENTER) {
				if (connexion()) {
					afficheMenuPrincipal();
					
					retourSansErreur();
					
					stage.close();
				}
			}
		}
		
	}
	
	private void retourSansErreur() {
		for (Label lbl : arrLbl) {
			lbl.setTextFill(Color.WHITE);
		}
		
		if (blnProfPasDeStyle) {
			for (TextField textField : arrTextField) {
				textField.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID,  CornerRadii.EMPTY, new BorderWidths(1))));
			}
			pwdFieldPreposePwd.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID,  CornerRadii.EMPTY, new BorderWidths(1))));
		}
		
		lblMsgErreur.setText("");
	}
	
	private Font font(int intSize, FontWeight fontWeight) {
		return Font.font("Serif", fontWeight == null ? FontWeight.NORMAL : fontWeight, intSize);
	}
	
	public static void main(String[] args) {
        launch(args);
    }
}