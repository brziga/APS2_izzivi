import java.util.Scanner;

class izziv8 {
    public static void main(String[] args){
        //branje vhodov
        Scanner	gorazd = new Scanner(System.in);
        int n = gorazd.nextInt();
        int vel = (int) Math.pow(2, Math.ceil(Math.log(n+n)/Math.log(2)));
        double[] kp1 = new double[vel]; //koeficienti polinoma 1
        double[] kp2 = new double[vel]; //koeficienti polinoma 2
        /* if(vel>4){
            kp1[20]=1.0;
        } */
        for(int i=0; i<vel; i++){
            if(i<n) kp1[i] = gorazd.nextDouble(); else kp1[i] = 0.0;
        }
        for(int i=0; i<vel; i++){
            if(i<n) kp2[i] = gorazd.nextDouble(); else kp2[i] = 0.0;
        }
        gorazd.close();
        //


        Complex[] kp1_fft = fft(kp1);
        Complex[] kp2_fft = fft(kp2);
        Complex[] zp = new Complex[vel];

        for(int i=0; i<vel; i++){
            zp[i] = kp1_fft[i].times(kp2_fft[i]);
        }

        Complex[] inverz = inverzfft(zp);

        for(int i=0; i<inverz.length; i++){
            inverz[i] = inverz[i].times(1.0/vel);
            System.out.printf("%s ",inverz[i].toString());
        }
        System.out.println();
        /* //test na roke
        Complex[] test = new Complex[4];
        test[0] = new Complex(0,0);
        test[1] = new Complex(7,-9);
        test[2] = new Complex(-6,0);
        test[3] = new Complex(7,9);
        Complex[] itest = inverzfft(test);
        double[] rtest = new double[4];
        for(int i=0; i<itest.length; i++){
            rtest[i] = (itest[i].re)*(1.0/4.0);
            System.out.printf("%f ",rtest[i]);
        }
        System.out.println();
        // */
    }

    public static Complex[] fft(double[] p){

        int n = p.length;

        //robni
        if(n == 1){
            Complex[] r = new Complex[1];
            r[0] = new Complex(p[0],0);
            //System.out.println(r[0].toString());
            return r;
        };
        //

        //delitev
        double[] psod = new double[n/2];
        double[] plih = new double[n/2];
        for(int i=0; i<n; i+=2){
            psod[i/2] = p[i];
            plih[i/2] = p[i+1];
        }
        //

        //rekurzija
        Complex[] rs = fft(psod);
        Complex[] rl = fft(plih);
        Complex[] r = new Complex[n];

        Complex w = new Complex(0,2*Math.PI/n).exp();
        Complex wk = new Complex(1, 0);

        for(int i=0; i<n/2; i++){
            r[i] = rs[i].plus(wk.times(rl[i]));
            r[i+n/2] = rs[i].minus(wk.times(rl[i]));
            wk = wk.times(w);
        }
        //

        //izpis
        for(int i=0; i<r.length; i++){
            System.out.printf("%s ", r[i].toString());
        }
        System.out.println();
        //

        return r;
    }

    public static Complex[] inverzfft(Complex[] p){

        int n = p.length;

        //robni
        if(n == 1){
            return p;
        };
        //

        //delitev
        Complex[] psod = new Complex[n/2];
        Complex[] plih = new Complex[n/2];
        for(int i=0; i<n; i+=2){
            psod[i/2] = p[i];
            plih[i/2] = p[i+1];
        }
        //

        //rekurzija
        Complex[] rs = inverzfft(psod);
        Complex[] rl = inverzfft(plih);
        Complex[] r = new Complex[n];

        Complex w = new Complex(0,-2*Math.PI/n).exp();
        Complex wk = new Complex(1, 0);

        for(int i=0; i<n/2; i++){
            r[i] = rs[i].plus(wk.times(rl[i]));
            r[i+n/2] = rs[i].minus(wk.times(rl[i]));
            wk = wk.times(w);
        }
        //

        //izpis
        for(int i=0; i<r.length; i++){
            System.out.printf("%s ", r[i].toString());
        }
        System.out.println();
        //

        return r;
    }

}

class Complex{
	double re;
	double im;

    public Complex(double real, double imag) {
        re = real;
        im = imag;
    }

    public String toString() {
    	double tRe = (double)Math.round(re * 100000) / 100000;
    	double tIm = (double)Math.round(im * 100000) / 100000;
        if (tIm == 0) return tRe + "";
        if (tRe == 0) return tIm + "i";
        if (tIm <  0) return tRe + "-" + (-tIm) + "i";
        return tRe + "+" + tIm + "i";
    }

	public Complex conj() {
		return new Complex(re, -im);
	}

    // sestevanje
    public Complex plus(Complex b) {
        Complex a = this;
        double real = a.re + b.re;
        double imag = a.im + b.im;
        return new Complex(real, imag);
    }

    // odstevanje
    public Complex minus(Complex b) {
        Complex a = this;
        double real = a.re - b.re;
        double imag = a.im - b.im;
        return new Complex(real, imag);
    }

    // mnozenje z drugim kompleksnim stevilo
    public Complex times(Complex b) {
        Complex a = this;
        double real = a.re * b.re - a.im * b.im;
        double imag = a.re * b.im + a.im * b.re;
        return new Complex(real, imag);
    }

    // mnozenje z realnim stevilom
    public Complex times(double alpha) {
        return new Complex(alpha * re, alpha * im);
    }

    // reciprocna vrednost kompleksnega stevila
    public Complex reciprocal() {
        double scale = re*re + im*im;
        return new Complex(re / scale, -im / scale);
    }

    // deljenje
    public Complex divides(Complex b) {
        Complex a = this;
        return a.times(b.reciprocal());
    }

    // e^this
    public Complex exp() {
        return new Complex(Math.exp(re) * Math.cos(im), Math.exp(re) * Math.sin(im));
    }


    //potenca komplesnega stevila
    public Complex pow(int k) {

    	Complex c = new Complex(1,0);
    	for (int i = 0; i <k ; i++) {
			c = c.times(this);
		}
    	return c;
    }

}