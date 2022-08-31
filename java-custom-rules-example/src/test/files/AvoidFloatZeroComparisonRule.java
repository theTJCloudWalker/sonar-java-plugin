class AvoidFloatZeroComparisonRule{
  void test(){
      float f1=0.0f;
      for(int i=0;i<11;i++){
        f1+=0.1f;
      }
      float f2=0.1f*11;
      if(f1==f2) { // Noncompliant

      }
  }

  void IsZero(float n){
    if(n==0.0f) { // Noncompliant

    }
  }
}
