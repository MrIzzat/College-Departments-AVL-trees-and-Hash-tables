
public class OHash{

	private HNode<Student>[ ] table;
	private int originalSize;

	public OHash(int size){
		originalSize=2*size;
		table = new HNode[2*size];
		ini(table);
	}
	private void ini(HNode<Student>[] table){
		for(int i=0;i< table.length;i++){
			table[i]= new HNode<Student>(null,'E');
		}
	}
	public void insert(Student data){

		int key = data.hashhh()%table.length;


		if(table[key].getFlag()=='E'||table[key].getFlag()=='D'){
			table[key]= new HNode(data,'F');
		}
		else{
			boolean flag = false;
			int s=0;
			int index=key;
			while(flag==false){
				if(table[index].getFlag()!='F'){
					table[index]= new HNode(data,'F');
					flag =true;
				}else{

					index=key+(s*s)%table.length;
					s++;

					if(index>=table.length)
						index%=table.length;

				}
			}

		}
		if(numOfItems()>=table.length/2){
			reHash();
		}

	}
	public void print(){
		for(int i=0;i<table.length;i++){
			if(table[i].getFlag()=='F')
				System.out.println(i+"-->"+table[i].toString()+"\n");
			else {
				System.out.println(i+"-->"+"null"+"\n");
			}
		}
		System.out.println("Done");
	}
	public int numOfItems(){
		int count=0;
		for(int i=0;i<table.length;i++){
			if(table[i].getFlag()=='F'){
				count++;
			}
		}
		return count;
	}
	
	private  int isPrime(int num){
		int prime = 1;
		for(int i = 2; i < num; i++) {
			if((num % i) == 0) {
				prime = 0;
			}
		}
		return num;
	}
	
	
	private  int nextPrime(int num) {
		num++;
		for (int i = 2; i < num; i++) {
			if(num%i == 0) {
				num++;
				i=2;
			} else {
				continue;
			}
		}
		return num;
	}
	private void reHash(){

		int newSize = nextPrime(table.length*2);
		int key=0;

		HNode<Student>[] temp = new HNode[newSize];
		ini(temp);

		for(int i=0;i<table.length;i++){
			if(table[i].getFlag()=='F'){
				key = table[i].getData().hashhh()%temp.length;
				if(temp[key].getFlag()=='E'){
					temp[key]=table[i];
				}else{
					boolean flag = false;
					int s=0;
					int index=key;
					while(!flag){
						if(temp[index].getFlag()!='F'){
							temp[index]=table[i];
							flag =true;
						}else{

							index = key+(s*s);
							s++;

							if(index>=temp.length){
								index%=temp.length;
							}
						}
					}

				}
			}


		}
		table = temp;
	}
	public Student delete(String name) {
		
		if(numOfItems()==0) {
		//	System.out.println("Error hash is empty");
			return null;
		}else {
			
			int key = (int) name.charAt(0)+name.charAt(name.length()-1);
			int index = key%table.length;
			int s=0;

			for(int i=0;i<table.length;i++,index=(key+s*s)%table.length,s++) {
				if(table[index].getFlag()=='E'||table[index].getFlag()=='D') {
					continue;
				}
				if(table[index].getData().getName().compareTo(name)==0) {
					table[index].setFlag('D');
					Student returned = table[index].getData();
					if(numOfItems()<=table.length/4) { //if i want to make it so it shrinks instantly to the size that best suits the data
						shrink();						// i can change the if statement to a while loop and add a if statement for case 0
					}
					return	returned;
				}
			}

			return null;
		}
	}
	public Student search(String name) {
		
		int key = (int) name.charAt(0)+name.charAt(name.length()-1);
		int index = key%table.length;
		int s=0;
		
		if(numOfItems()==0) {
			return null;
		}else {
			
			for(int i=0;i<table.length;i++,s++,index=(key+s*s)%table.length ){
				if(table[index].getData()==null) {
					return null;
				}
				if(table[index].getFlag()=='D') {
					continue;
				}
				if(table[index].getData().getName().compareTo(name)==0) {
					//System.out.println(table[index].getData().toString());
					return table[index].getData();
				}
			}
			return null;

		}
	}
	private void shrink() {

		int newSize = table.length/2;

		int key=0;

		HNode<Student>[] temp = new HNode[newSize];

		ini(temp);

		for(int i=0;i<table.length;i++) {
			if(table[i].getFlag()=='F') {


				key=table[i].getData().hashhh()%temp.length;
				if(table[i].getFlag()=='D'||table[i].getFlag()=='E') {
					temp[key]=table[i];
				}else {
					int index=key,s=0;
					boolean flag=false;
					while(!flag) {
						if(temp[index].getFlag()!='F') {
							temp[index]=table[i];
							flag=true;
						}else {
							index=key+s*s;
							s++;
							if(index>=temp.length) {
								index%=temp.length;
							}
						}

					}
				}

			}
		}
		table = temp;



	}
	public String toString(){
		String str="";
		for(int i=0;i<table.length;i++){
			if(table[i].getFlag()=='F') {
				str +=i+"-->"+table[i].toString()+"\n";
			}
			else {
				str+=i+"-->"+"null"+"\n";
			}
		}
		return str;
	}
	public void clear() {
//		int size = table.length;
		table = new HNode[originalSize];
		ini(table);
	}
	public int Size() {
		return table.length;
	}
	public String forFile() {
		
		String str="";
		for(int i=0;i<table.length;i++){
			if(table[i].getFlag()=='F') {
				str +=table[i].getData().forFile()+"\n";
			}
		}
		return str;
		
	}
}
