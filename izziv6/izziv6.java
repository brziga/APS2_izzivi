import java.util.Scanner;


class Matrix {


	private int[][] m;

	public int n; //only square matrices

	public Matrix(int n){

		this.n = n;

		m = new int[n][n];

	}


    //set value at i,j
	public void setV(int i, int j, int val){

		m[i][j] = val;

	}


    // get value at index i,j
	public int v(int i, int j){

		return m[i][j];

	}


    // return a square submatrix from this
	public Matrix getSubmatrix(int startRow, int startCol, int dim){

		Matrix subM = new Matrix(dim);

		for (int i = 0; i<dim ; i++ )

			for (int j=0;j<dim ; j++ )

				subM.setV(i,j, m[startRow+i][startCol+j]);



		return subM;

	}


    // write this matrix as a submatrix from b (useful for the result of multiplication)
	public void putSubmatrix(int startRow, int startCol, Matrix b){

		for (int i = 0; i<b.n ; i++ )

			for (int j=0;j<b.n ; j++ )

				setV(startRow+i,startCol+j, b.v(i,j));

	}


    // matrix addition
	public Matrix sum(Matrix b){

		Matrix c = new Matrix(n);

		for(int i = 0; i< n;i++){

			for(int j = 0; j<n;j++){

				c.setV(i, j, m[i][j]+b.v(i, j));

			}

		}

		return c;

	}





    // matrix subtraction
	public Matrix sub(Matrix b){

		Matrix c = new Matrix(n);

		for(int i = 0; i< n;i++){

			for(int j = 0; j<n;j++){

				c.setV(i, j, m[i][j]-b.v(i, j));

			}

		}

		return c;

	}



	//simple multiplication
	public Matrix mult(Matrix b){
        Matrix rez = new Matrix(b.n);
        for(int i=0; i<rez.n; i++){
            for(int j=0; j<rez.n; j++){
                int tmp = 0;
                for(int k=0; k<n; k++){
                    tmp += m[i][k]*b.v(k, j);
                }
                rez.setV(i, j, tmp);
            }
        }
        return rez;
	}


    // Strassen multiplication
	public Matrix multStrassen(Matrix b, int cutoff){
        if(n == cutoff) {
            return this.mult(b);
        }
        else{
            Matrix c = new Matrix(n);
            Matrix a11 = this.getSubmatrix(0, 0, n/2);
            Matrix a12 = this.getSubmatrix(0, n/2, n/2);
            Matrix a21 = this.getSubmatrix(n/2, 0, n/2);
            Matrix a22 = this.getSubmatrix(n/2, n/2, n/2);
            Matrix b11 = b.getSubmatrix(0, 0, b.n/2);
            Matrix b12 = b.getSubmatrix(0, b.n/2, b.n/2);
            Matrix b21 = b.getSubmatrix(b.n/2, 0, b.n/2);
            Matrix b22 = b.getSubmatrix(b.n/2, b.n/2, b.n/2);
            Matrix m1 = (a11.sum(a22)).multStrassen(b11.sum(b22), cutoff);
            Matrix m2 = (a21.sum(a22)).multStrassen(b11, cutoff);
            Matrix m3 = a11.multStrassen(b12.sub(b22), cutoff);
            Matrix m4 = a22.multStrassen(b21.sub(b11), cutoff);
            Matrix m5 = (a11.sum(a12)).multStrassen(b22, cutoff);
            Matrix m6 = (a21.sub(a11)).multStrassen(b11.sum(b12), cutoff);
            Matrix m7 = (a12.sub(a22)).multStrassen(b21.sum(b22), cutoff);
            Matrix c11 = m1.sum(m4).sub(m5).sum(m7);
            Matrix c12 = m3.sum(m5);
            Matrix c21 = m2.sum(m4);
            Matrix c22 = m1.sub(m2).sum(m3).sum(m6);
            c.putSubmatrix(0, 0, c11);
            c.putSubmatrix(0, c.n/2, c12);
            c.putSubmatrix(c.n/2, 0, c21);
            c.putSubmatrix(c.n/2, c.n/2, c22);
            /* //print m_1-7
            System.out.printf("m1: %d\n",m1.sumEl());
            System.out.printf("m2: %d\n",m2.sumEl());
            System.out.printf("m3: %d\n",m3.sumEl());
            System.out.printf("m4: %d\n",m4.sumEl());
            System.out.printf("m5: %d\n",m5.sumEl());
            System.out.printf("m6: %d\n",m6.sumEl());
            System.out.printf("m7: %d\n",m7.sumEl()); */
            return c;
        }
	}

    //print matrix to stdout
    public void print(){
        for(int i=0; i<this.n; i++){
            for(int j=0; j<this.n; j++){
                System.out.printf("%d%s",this.v(i, j), j==n-1 ? "" : " ");
            }
            System.out.println();
        }
    }

    //sum of all elements
    public int sumEl(){
        int res = 0;
        for(int i=0; i<this.n; i++){
            for(int j=0; j<this.n; j++){
                res += this.v(i, j);
            }
        }
        return res;
    }


}




public class izziv6{

    public static void branjeMatrike(Matrix m, Scanner vhod){
        for(int i=0; i<m.n; i++){
            for(int j=0; j<m.n; j++){
                m.setV(i, j, vhod.nextInt());
            }
        }
    }

	public static void main(String[] args) {

        //branje vhodnih podatkov
		Scanner joze = new Scanner(System.in);
        int n = joze.nextInt();
        int meja = joze.nextInt();
        Matrix a = new Matrix(n);
        Matrix b = new Matrix(n);
        branjeMatrike(a,joze);
        branjeMatrike(b,joze);
        joze.close();
        
        //magic
        /* Matrix rez_test1_debug = a.mult(b); */
        Matrix rezultat = a.multStrassen(b, meja);
        rezultat.print();
	}



}
