
//For General purpose functions
public class General{

    //Kinda like index%size, but it handles -1 to making indexing easier
        //Direction is False for left and True for right
    public int shift(int index, int size, Boolean direction){
        if(direction == false){
            if(index == 0){
                return size - 1;
            }
            else{
                return index - 1;
            }
        }
        else{
            if(index == size - 1){
                return 0;
            }
            else{
                return index + 1;
            }
        }
    }

}
