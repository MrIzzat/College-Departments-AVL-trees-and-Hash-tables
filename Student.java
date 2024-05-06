
public class Student implements Comparable<Student> {

	private int ID;
	private String name;
	private String gender;
	private double average;


	public Student(int iD, String name, String gender, double average) {
		ID = iD;
		this.name = name;
		this.gender = gender;
		this.average = average;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public double getAverage() {
		return average;
	}
	public void setAverage(double average) {
		this.average = average;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	public int hashhh(){
		
		return (int) (name.charAt(0)+name.charAt(name.length()-1));
	}
	@Override
	public int compareTo(Student o) {
		return this.name.compareTo(o.name);
	}
	public String toString(){
		return name+" "+ID+" "+gender+" "+average;
	}
	public int unihashh(){ // for uni id

		return ID%10000;


	}
	public String forFile(){
		return name+"/"+ID+"/"+average+"/"+gender;
	}

}
