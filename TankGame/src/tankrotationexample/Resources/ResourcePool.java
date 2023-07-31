package tankrotationexample.Resources;

import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ResourcePool <T>{
    private final String resourceType;
    private final List<T> pool;

    public ResourcePool(String type, int size){
        this.resourceType = type;
        this.pool = new ArrayList<>(size);
    }

    public boolean fillPool(Class<T> type, int size){
        try{
            for(int i = 0; i< size; i++){
                this.pool.add(
                        type.getDeclaredConstructor(Float.TYPE,
                                Float.TYPE,
                                BufferedImage.class).newInstance(0,0,ResourceManager.getSprite(this.resourceType))
                );
            }
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | RuntimeException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    public T getResource(){
        return this.pool.remove(this.pool.size()-1);
    }

    public boolean addResource(T obj){
        return this.pool.add(obj);
    }
}
