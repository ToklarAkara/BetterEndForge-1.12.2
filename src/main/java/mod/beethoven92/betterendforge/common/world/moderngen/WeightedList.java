package mod.beethoven92.betterendforge.common.world.moderngen;

import com.google.common.collect.Lists;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class WeightedList<U> {
    protected final List<WeightedList.Entry<U>> entries;
    private final Random random = new Random();

    public WeightedList() {
        this(Lists.newArrayList());
    }

    private WeightedList(List<WeightedList.Entry<U>> p_i231541_1_) {
        this.entries = Lists.newArrayList(p_i231541_1_);
    }


    public WeightedList<U> add(U p_226313_1_, int p_226313_2_) {
        this.entries.add(new WeightedList.Entry(p_226313_1_, p_226313_2_));
        return this;
    }

    public WeightedList<U> shuffle() {
        return this.shuffle(this.random);
    }

    public WeightedList<U> shuffle(Random p_226314_1_) {
        this.entries.forEach((p_234004_1_) -> {
            p_234004_1_.setRandom(p_226314_1_.nextFloat());
        });
        this.entries.sort(Comparator.comparingDouble((p_234003_0_) -> {
            return p_234003_0_.getRandWeight();
        }));
        return this;
    }

    public boolean isEmpty() {
        return this.entries.isEmpty();
    }

    public Stream<U> stream() {
        return this.entries.stream().map(WeightedList.Entry::getData);
    }

    public U getOne(Random p_226318_1_) {
        return this.shuffle(p_226318_1_).stream().findFirst().orElseThrow(RuntimeException::new);
    }

    public String toString() {
        return "WeightedList[" + this.entries + "]";
    }

    public static class Entry<T> {
        private final T data;
        private final int weight;
        private double randWeight;

        private Entry(T p_i231542_1_, int p_i231542_2_) {
            this.weight = p_i231542_2_;
            this.data = p_i231542_1_;
        }

        private double getRandWeight() {
            return this.randWeight;
        }

        private void setRandom(float p_220648_1_) {
            this.randWeight = -Math.pow((double)p_220648_1_, (double)(1.0F / (float)this.weight));
        }

        public T getData() {
            return this.data;
        }

        public String toString() {
            return "" + this.weight + ":" + this.data;
        }
    }
}