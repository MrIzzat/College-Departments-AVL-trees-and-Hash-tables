import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import javafx.*;
import javafx.application.*;
import javafx.geometry.*;




public class Main extends Application{

	Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();

	Button back = new Button();
	Button back2 = new Button();
	Button back3 = new Button();

	Button printDepartment = new Button();
	Button searchDepartment = new Button();
	Button addDepartment = new Button();
	Button deleteDepartment = new Button();
	Button treeHeight = new Button();

	Button printHash = new Button();
	Button printSize = new Button();
	Button printFunction = new Button();
	Button addStudent = new Button();
	Button searchStudent = new Button();
	Button deleteStudent = new Button();

	Stage hashStage = new Stage();

	static AVLTree<String> Departments = new AVLTree<>();
	static OHash Students = new OHash(2);
	static File currentStudentFile;

	public static void main(String[] args) throws IOException{

		readFile();	

		launch(args);

	}

	public void start(Stage primaryStage) throws IOException {

		primaryStage.setTitle("Select an Option");
		primaryStage.setScene(mainMenu());

		printDepartment.setOnAction(e ->{

			primaryStage.setTitle("List of Departments");
			primaryStage.setScene(printDepartments());	

		});

		searchDepartment.setOnAction(e ->{

			primaryStage.setTitle("Search for a Department");
			primaryStage.setScene(searchDepartment());	

		});
		addDepartment.setOnAction(e ->{

			primaryStage.setTitle("Add a Department");
			try {
				primaryStage.setScene(addDepartment());
			} catch (IOException e1) {
				e1.printStackTrace();
			}	
		});

		deleteDepartment.setOnAction(e ->{

			primaryStage.setTitle("Delete a Department");
			primaryStage.setScene(deleteDepartment());	

		});

		back.setOnAction(e ->{

			primaryStage.setTitle("Select an Option");
			primaryStage.setScene(mainMenu());

		});

		treeHeight.setOnAction(e -> {

			Alert a = new Alert(AlertType.NONE);

			a.setAlertType(AlertType.INFORMATION);

			a.setContentText("The Height of the tree is: " +Departments.Height()+"");

			a.show();

		});

		printSize.setOnAction(e -> {

			Alert a = new Alert(AlertType.NONE);

			a.setAlertType(AlertType.INFORMATION);

			a.setContentText("The size of the hash is: " + Students.Size()+" and it's using: "+Students.numOfItems()+" spaces");

			a.show();

		});

		printFunction.setOnAction(e -> {

			Alert a = new Alert(AlertType.NONE);

			a.setAlertType(AlertType.INFORMATION);

			a.setContentText("The function used is: ascii code of the first and last letters of the name");

			a.show();

		});

		printHash.setOnAction(e ->{

			hashStage.setTitle("List of Students");
			hashStage.setScene(printStudents());
			hashStage.show();


		});
		searchStudent.setOnAction(e ->{

			hashStage.setTitle("Search for a Student");
			hashStage.setScene(searchStudents());
			hashStage.show();


		});

		deleteStudent.setOnAction(e ->{

			hashStage.setTitle("Delete a Student");
			hashStage.setScene(deleteStudent());
			hashStage.show();


		});
		addStudent.setOnAction(e ->{

			hashStage.setTitle("Add a Student");
			hashStage.setScene(addStudent());
			hashStage.show();


		});

		back2.setOnAction(e->{
			hashStage.close();
			Students.clear();

		});


		back3.setOnAction(e->{
			hashStage.setScene(hashMenu());
		});

		primaryStage.show();

	}


	public Scene printDepartments() {

		GridPane Main = new GridPane();

		//		Text info = new Text("Departments:");
		TextArea Info = new TextArea();

		Info.setEditable(false);

		//		Main.add(info, 0, 0);
		Main.add(Info,1,0);
		Main.add(back, 1, 1);

		back.setText("Back");
		back.setStyle("-fx-background-color:#f4f4f4"  );
		back.setFont(Font.font("Calibri",FontWeight.BOLD,16));


		Info.setText(Departments.stringInOrder());



		Main.setAlignment(Pos.CENTER);

		return new Scene(Main,screenSize.getWidth(),screenSize.getHeight());

	}

	public Scene addDepartment() throws IOException {



		GridPane Main = new GridPane();

		Button add = new Button("Add");
		Button file = new Button("Choose a File");

		var Wrapper = new Object() {
			File studentInfo;
		};


		back.setText("Back");
		back.setStyle("-fx-background-color:#f4f4f4"  );
		back.setFont(Font.font("Calibri",FontWeight.BOLD,16));

		add.setText("Add");
		add.setStyle("-fx-background-color:#f4f4f4"  );
		add.setFont(Font.font("Calibri",FontWeight.BOLD,16));


		file.setStyle("-fx-background-color:#f4f4f4"  );
		file.setFont(Font.font("Calibri",FontWeight.BOLD,16));

		Text name = new Text("Deprtament Name:");

		TextField Name = new TextField();

		Main.setHgap(10);
		Main.setVgap(10);

		Main.add(name, 0, 0);
		Main.add(Name, 1, 0);

		Main.add(file, 1, 1);

		Main.add(add, 0, 2);
		Main.add(back, 2, 2);

		add.setOnAction(e ->{

			if(name.getText().isEmpty()) {
				Alert a = new Alert(AlertType.NONE);

				a.setAlertType(AlertType.INFORMATION);

				a.setContentText("Error: name textfield is empty");

				a.show();
			}else {

				String departmentName = Name.getText();

				File StudentInfo = Wrapper.studentInfo;

				if(StudentInfo==null) {

					Alert a = new Alert(AlertType.NONE);

					a.setAlertType(AlertType.INFORMATION);

					a.setContentText("Error: no file has been selected");

					a.show();

				}else {
					Departments.insert(departmentName, StudentInfo);
					try {
						writeDepartment();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					Alert a = new Alert(AlertType.NONE);

					a.setAlertType(AlertType.INFORMATION);

					a.setContentText("Department has been added.");

					a.show();

				}

				//	String input = departmentName+"/"+StudentInfo.getAbsolutePath();

				//System.out.println(input);





			}
		});

		file.setOnAction(e -> {

			FileChooser File = new FileChooser();
			Wrapper.studentInfo=File.showOpenDialog(null); //will need a try catch block to read the file


		});

		Main.setAlignment(Pos.CENTER);

		return new Scene(Main,screenSize.getWidth(),screenSize.getHeight());
	}

	public Scene searchDepartment() { 

		GridPane Main = new GridPane();

		Button search = new Button("Search");


		back.setText("Back");
		back.setStyle("-fx-background-color:#f4f4f4"  );
		back.setFont(Font.font("Calibri",FontWeight.BOLD,16));

		search.setText("Search");
		search.setStyle("-fx-background-color:#f4f4f4"  );
		search.setFont(Font.font("Calibri",FontWeight.BOLD,16));

		Text name = new Text("Department Name:");

		TextField Name = new TextField();

		Main.add(name, 0, 0);
		Main.add(Name,1, 0);

		Main.add(search, 0, 1);
		Main.add(back, 2, 1);

		Main.setHgap(10);
		Main.setVgap(10);


		search.setOnAction(e -> {

			if(Name.getText().isEmpty()) {
				Alert a = new Alert(AlertType.NONE);
				a.setAlertType(AlertType.INFORMATION);
				a.setContentText("Error: name textfield is empty");
				a.show();
			}else {
				Node x=Departments.Search(Name.getText());
				if(x==null) {
					Alert a = new Alert(AlertType.NONE);
					a.setAlertType(AlertType.INFORMATION); 
					a.setContentText("Error: department does not exist");
					a.show();
				}else {
					currentStudentFile=x.getStudentInfo();
					try {
						readStudents(x);
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					back2.setText("Back");
					back2.setStyle("-fx-background-color:#f4f4f4"  );
					back2.setFont(Font.font("Calibri",FontWeight.BOLD,16));

					hashStage.setTitle("Department of "+x.getData());
					hashStage.setScene(hashMenu());
					hashStage.show();

				}
			}
		});

		Main.setAlignment(Pos.CENTER);

		return new Scene(Main,screenSize.getWidth(),screenSize.getHeight());

	}

	public Scene deleteDepartment() {

		// primaryStage.setTitle("Delete a Product");

		GridPane Main = new GridPane();

		Button delete = new Button();

		back.setText("Back");
		back.setStyle("-fx-background-color:#f4f4f4"  );
		back.setFont(Font.font("Calibri",FontWeight.BOLD,16));

		delete.setText("Delete");
		delete.setStyle("-fx-background-color:#f4f4f4"  );
		delete.setFont(Font.font("Calibri",FontWeight.BOLD,16));

		Text name = new Text("Department Name:");

		TextField Name = new TextField();

		Main.add(name, 0, 0);
		Main.add(Name,1, 0);

		Main.add(delete, 0, 1);
		Main.add(back, 2, 1);

		Main.setHgap(10);
		Main.setVgap(10);


		delete.setOnAction(e -> {

			if(Name.getText().isEmpty()) {
				Alert a = new Alert(AlertType.NONE);

				a.setAlertType(AlertType.INFORMATION);

				a.setContentText("Error: name textfield is empty");

				a.show();
			}else {
				Node x=Departments.delete(Name.getText());
				if(x!=null) {
					try {
						writeDepartment();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					Alert a = new Alert(AlertType.NONE);
					a.setAlertType(AlertType.INFORMATION);  
					a.setContentText(x.toString()+" has been deleted");
					a.show();

				}else {
					Alert a = new Alert(AlertType.NONE);		
					a.setAlertType(AlertType.INFORMATION);     
					a.setContentText("Error: department does not exist");   
					a.show();

				}
			}

		});


		Main.setAlignment(Pos.CENTER);

		return new Scene(Main,screenSize.getWidth(),screenSize.getHeight());

	}

	public Scene mainMenu() {

		GridPane Main = new GridPane();


		printDepartment.setText("Print all Departments");
		printDepartment.setStyle("-fx-background-color:#f4f4f4"  );
		printDepartment.setFont(Font.font("Calibri",FontWeight.BOLD,16));


		searchDepartment.setText("Search for a Department");
		searchDepartment.setStyle("-fx-background-color:#f4f4f4"  );
		searchDepartment.setFont(Font.font("Calibri",FontWeight.BOLD,16));

		addDepartment.setText("Add a Deparment");
		addDepartment.setStyle("-fx-background-color:#f4f4f4"  );
		addDepartment.setFont(Font.font("Calibri",FontWeight.BOLD,16));

		deleteDepartment.setText("Delete a Department");
		deleteDepartment.setStyle("-fx-background-color:#f4f4f4"  );
		deleteDepartment.setFont(Font.font("Calibri",FontWeight.BOLD,16));

		treeHeight.setText("Calculate Tree Height");
		treeHeight.setStyle("-fx-background-color:#f4f4f4"  );
		treeHeight.setFont(Font.font("Calibri",FontWeight.BOLD,16));

		Main.add(printDepartment, 0, 0);
		Main.add(searchDepartment, 0, 1);
		Main.add(addDepartment, 0, 2);
		Main.add(deleteDepartment, 0, 3);
		Main.add(treeHeight, 0, 4);

		Main.setAlignment(Pos.CENTER);

		Main.setHgap(20);


		return new Scene(Main,screenSize.getWidth(),screenSize.getHeight());

	}

	public Scene hashMenu() { 

		GridPane Main = new GridPane();


		printHash.setText("Print Students");
		printHash.setStyle("-fx-background-color:#f4f4f4"  );
		printHash.setFont(Font.font("Calibri",FontWeight.BOLD,16));

		printSize.setText("Show Hash Size");
		printSize.setStyle("-fx-background-color:#f4f4f4"  );
		printSize.setFont(Font.font("Calibri",FontWeight.BOLD,16));


		printFunction.setText("Show Hash Function");
		printFunction.setStyle("-fx-background-color:#f4f4f4"  );
		printFunction.setFont(Font.font("Calibri",FontWeight.BOLD,16));

		addStudent.setText("Add a Student");
		addStudent.setStyle("-fx-background-color:#f4f4f4"  );
		addStudent.setFont(Font.font("Calibri",FontWeight.BOLD,16));

		searchStudent.setText("Search for a Student");
		searchStudent.setStyle("-fx-background-color:#f4f4f4"  );
		searchStudent.setFont(Font.font("Calibri",FontWeight.BOLD,16));

		deleteStudent.setText("Delete a Student");
		deleteStudent.setStyle("-fx-background-color:#f4f4f4"  );
		deleteStudent.setFont(Font.font("Calibri",FontWeight.BOLD,16));

		Main.add(printHash, 0, 0);
		Main.add(printSize, 0, 1);
		Main.add(printFunction, 0, 2);
		Main.add(addStudent, 0, 3);
		Main.add(searchStudent, 0, 4);
		Main.add(deleteStudent, 0, 5);
		Main.add(back2, 1, 6);

		Main.setAlignment(Pos.CENTER);

		Main.setHgap(20);


		return new Scene(Main,screenSize.getWidth(),screenSize.getHeight());

	}

	public Scene printStudents() {

		GridPane Main = new GridPane();

		//		Text info = new Text("Departments:");
		TextArea Info = new TextArea();

		Info.setEditable(false);

		//		Main.add(info, 0, 0);
		Main.add(Info,1,0);
		Main.add(back3, 1, 1);

		back3.setText("Back");
		back3.setStyle("-fx-background-color:#f4f4f4"  );
		back3.setFont(Font.font("Calibri",FontWeight.BOLD,16));

		//System.out.println(Students.toString());

		Info.setText(Students.toString());

		Main.setAlignment(Pos.CENTER);

		return new Scene(Main,screenSize.getWidth(),screenSize.getHeight());

	}

	public Scene searchStudents() {

		GridPane Main = new GridPane();

		Button search = new Button("Search");

		back3.setText("Back");
		back3.setStyle("-fx-background-color:#f4f4f4"  );
		back3.setFont(Font.font("Calibri",FontWeight.BOLD,16));

		search.setText("Search");
		search.setStyle("-fx-background-color:#f4f4f4"  );
		search.setFont(Font.font("Calibri",FontWeight.BOLD,16));

		Text name = new Text("Student full name: ");

		TextField Name = new TextField();

		Text info = new Text("Results:");

		TextArea Info = new TextArea();

		Main.add(name, 0, 0);
		Main.add(Name,1, 0);
		Main.add(info, 0, 1);
		Main.add(Info, 1, 1);
		Main.add(search, 0, 2);
		Main.add(back3, 2, 2);

		Main.setHgap(10);
		Main.setVgap(10);


		search.setOnAction(e -> {

			if(Name.getText().isEmpty()) {
				Alert a = new Alert(AlertType.NONE);

				a.setAlertType(AlertType.INFORMATION);

				a.setContentText("Error: name textfield is empty");

				a.show();
			}else {
				Student temp = Students.search(Name.getText());
				if(temp==null) {
					Info.setText("Student could not be found");	
				}else {
					Info.setText(temp.toString());
				}
			}

		});


		Main.setAlignment(Pos.CENTER);

		return new Scene(Main,screenSize.getWidth(),screenSize.getHeight());
	}
	public Scene deleteStudent() {

		GridPane Main = new GridPane();

		Button delete = new Button();

		back3.setText("Back");
		back3.setStyle("-fx-background-color:#f4f4f4"  );
		back3.setFont(Font.font("Calibri",FontWeight.BOLD,16));

		delete.setText("Delete");
		delete.setStyle("-fx-background-color:#f4f4f4"  );
		delete.setFont(Font.font("Calibri",FontWeight.BOLD,16));

		Text name = new Text("Student full name: ");

		TextField Name = new TextField();



		Main.add(name, 0, 0);
		Main.add(Name,1, 0);
		Main.add(delete, 0, 1);
		Main.add(back3, 2, 1);

		Main.setHgap(10);
		Main.setVgap(10);


		delete.setOnAction(e -> {

			if(Name.getText().isEmpty()) {
				Alert a = new Alert(AlertType.NONE);

				a.setAlertType(AlertType.INFORMATION);

				a.setContentText("Error: name textfield is empty");

				a.show();
			}else {
				Student temp = Students.delete(Name.getText());
				if(temp==null) {
						Alert a = new Alert(AlertType.NONE);

						a.setAlertType(AlertType.INFORMATION);

						a.setContentText("Student could not be deleted, they don't exist ");

						a.show();
					}else {
							Alert a = new Alert(AlertType.NONE);

							a.setAlertType(AlertType.INFORMATION);

							a.setContentText("Student has been deleted");

							a.show();
							try {
								writeStudent();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						
					
				}
			}

		});


		Main.setAlignment(Pos.CENTER);

		return new Scene(Main,screenSize.getWidth(),screenSize.getHeight());
	}
	public Scene addStudent() {
		
		GridPane Main = new GridPane();

		Button add = new Button("Add");

		back3.setText("Back");
		back3.setStyle("-fx-background-color:#f4f4f4"  );
		back3.setFont(Font.font("Calibri",FontWeight.BOLD,16));

		add.setText("Add");
		add.setStyle("-fx-background-color:#f4f4f4"  );
		add.setFont(Font.font("Calibri",FontWeight.BOLD,16));
		
		Text name = new Text("Student's Name:");
		Text id = new Text("Student's ID:");
		Text average = new Text("Student's Average:");
		Text gender = new Text("Student's Gender:");

		TextField ID = new TextField();
		TextField Name = new TextField();
		TextField Average = new TextField();
		TextField Gender = new TextField();

		Main.setHgap(10);
		Main.setVgap(10);

		Main.add(id, 0, 1);
		Main.add(ID, 1, 1);

		Main.add(name, 0, 0);
		Main.add(Name, 1, 0);

		Main.add(average, 0, 2);
		Main.add(Average, 1, 2);


		Main.add(gender, 0, 3);
		Main.add(Gender, 1, 3);

		Main.add(add, 0, 4);
		Main.add(back3, 2, 4);

		add.setOnAction(e ->{

			if(ID.getText().isEmpty()||Name.getText().isEmpty()||Average.getText().isEmpty()||Gender.getText().isEmpty()) {
				Alert a = new Alert(AlertType.NONE);

				a.setAlertType(AlertType.INFORMATION);

				a.setContentText("Error: a textfield is empty");

				a.show();
			}else {
				int iD = Integer.parseInt(ID.getText());
				String namE = Name.getText();
				double avg = Double.parseDouble(Average.getText());
				String gendeR = Gender.getText();
				
				Students.insert(new Student(iD,namE,gendeR,avg));
				
				try {
					writeStudent();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				Alert a = new Alert(AlertType.NONE);

				a.setAlertType(AlertType.INFORMATION);

				a.setContentText("Student has been added");

				a.show();
			}
		});

		Main.setAlignment(Pos.CENTER);

		return new Scene(Main,screenSize.getWidth(),screenSize.getHeight());
	}
	
	public static void readFile() throws IOException {

		File departmentInfo = new File("departments.txt");

		Scanner sc = new Scanner(departmentInfo);

		String info[];
		String filename,dName;

		while(sc.hasNextLine()) {
			info=sc.nextLine().split("/");
			dName=info[0];
			filename=info[1];
			//	System.out.println(dName+" "+filename);

			Departments.insert(dName, new File(filename));

		}
		sc.close();
	}

	public  void writeDepartment() throws IOException{

		PrintWriter write = new PrintWriter(new File("departments.txt"));


		write.write(Departments.fileInOrder());

		write.close();

	}

	public void readStudents(Node x) throws IOException{

		File file = x.getStudentInfo();
		
		try {

		Scanner sc = new Scanner(file);
		
		String[] strings;

		String name;
		int ID=0;
		double avg=0;
		String gender;

		while(sc.hasNextLine()) {

			strings = sc.nextLine().split("/");

			name=strings[0];
			ID = Integer.parseInt(strings[1]);
			avg=Double.parseDouble(strings[2]);
			gender=strings[3];

			Students.insert(new Student(ID,name,gender,avg));

		}
		sc.close();
		}catch(FileNotFoundException e) {
			PrintWriter create = new PrintWriter(file);
			create.write("");
			create.close();
		}



	}

	public  void writeStudent() throws IOException{

		PrintWriter write = new PrintWriter(currentStudentFile);
		

//		write.println(Students.forFile());
		
		write.write(Students.forFile());

		write.close();

	}
}
