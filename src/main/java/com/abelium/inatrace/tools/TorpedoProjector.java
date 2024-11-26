package com.abelium.inatrace.tools;

import jakarta.persistence.EntityManager;
import org.torpedoquery.jakarta.jpa.Torpedo;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class TorpedoProjector<P, T> {
	
    public static class Projection<P, T, V>
    {
    	private V proxyValue;
        private BiConsumer<T, V> setter;

        public Projection(V proxyValue, BiConsumer<T, V> setter) {
            this.proxyValue = proxyValue;
            this.setter = setter;
        }
        
        public V getProxyValue() {
			return proxyValue;
		}

		@SuppressWarnings("unchecked")
		public void applyTo(T result, Object value) {
			setter.accept(result, (V) value);
			
		}
    }
		
    private P proxy;
	private Class<T> apiClass;
	private List<Projection<P, T, ?>> projections = new ArrayList<>();

	public TorpedoProjector(P proxy, Class<T> apiClass) {
		this.proxy = proxy;
		this.apiClass = apiClass;
	}
	
	public <V> TorpedoProjector<P, T> add(V proxyValue, BiConsumer<T, V> setter) {
		projections.add(new Projection<P, T, V>(proxyValue, setter));
		return this;
	}
	
	public List<T> list(EntityManager em, int offset, int limit) {
		Object[] selArray = projections.stream().map(Projection::getProxyValue).toArray(Object[]::new);
		List<Object[]> valList = Torpedo.select(selArray).setFirstResult(offset).setMaxResults(limit).list(em);
		List<T> result = new ArrayList<>();
		
		if (valList.isEmpty()) return result;
	
		List<Object[]> valArrays = new ArrayList<>(valList.size());
		if (selArray.length == 1) {
			for (Object o : valList) {
				valArrays.add(new Object[] { o });
			}
		} else {
			for (Object o : valList) {
				valArrays.add((Object[]) o);
			}
		}

		for (Object[] val : valArrays) {
			result.add(createResultItem(val));
		}
		return result;
	}
	
	public long count(EntityManager em) {
		return Torpedo.select(Torpedo.count(proxy)).get(em).orElse(0L);
	}
		
	private T createResultItem(Object[] values) {
		try {
			T result = apiClass.getConstructor().newInstance();
			for (int i = 0; i < values.length; i++) {
				projections.get(i).applyTo(result, values[i]);
			}
			return result;
		} catch (Exception e) {
			throw new RuntimeException("Error creating API class " + apiClass.getCanonicalName(), e);
		}
	}

	
}
