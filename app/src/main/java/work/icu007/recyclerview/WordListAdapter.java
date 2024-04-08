package work.icu007.recyclerview;

/*
 * Author: Charlie_Liao
 * Time: 2023/5/16-17:30
 * E-mail: rookie_l@icu007.work
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class WordListAdapter extends
        RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    // 用于保存数据，实例化一个LinkedList 字符类型为String
    private final LinkedList<String> mWordList;
    // LayoutInflater 读取布局 XML 描述并将其转换为相应的 View 项。
    private LayoutInflater mInflater;

    // 构造函数需要一个上下文参数，以及一个包含应用程序数据的单词链表
    // 这个方法需要实例化一个用于 mFlaater 的 LayoutFlaater，并将 mWordList 设置为传入的数据:

    /**
     *
     * LayoutInflater是Android中的一个类，用于将XML布局文件转换为相应的视图对象。
     * 它充当了布局填充器的角色，负责解析布局文件并创建对应的视图层次结构。
     * LayoutInflater的主要作用是根据布局文件的描述，动态地将布局文件实例化为具体的视图对象。
     * 通过LayoutInflater，可以在代码中将布局文件转换为实际可见的视图，以便在应用程序中进行操作和显示。
     *
     * 使用LayoutInflater的一般步骤：
     * 1. 获取LayoutInflater的实例
     * LayoutInflater mInflater;
     * this.mInflater = LayoutInflater.from(context);
     * 2. 使用LayoutInflater实例化布局文件
     * View mItemView = mInflater.inflate(R.layout.wordlist_item,parent,false);
     * 3. 操作和显示视图
     * @param context
     * @param mWordList
     */
    public WordListAdapter(Context context , LinkedList<String> mWordList) {
        this.mInflater = LayoutInflater.from(context);
        this.mWordList = mWordList;
    }

    /**
     *
     * onCreateViewHolder()方法类似于onCreate()方法，它会填充布局并返回一个包含布局和适配器的ViewHolder。
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.wordlist_item, parent,false);
        return new WordViewHolder(mItemView, this);
    }

    /**
     *
     * OnBindViewHolder () 方法将数据连接到视图持有者。
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        String mCurrent = mWordList.get(position);
        holder.wordItemView.setText(mCurrent);
    }

    // 返回mWordList.size()来getItemCount
    @Override
    public int getItemCount() {
        return mWordList.size();
    }

    public class WordViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        // 内部类TextView 和 WordListAdapter变量
        public final TextView wordItemView;
        final WordListAdapter mAdapter;

        /**
         *
         *
         * @param itemView 用于显示数据
         * @param mAdapter 用于管理data和view
         */
        public WordViewHolder(View itemView, WordListAdapter mAdapter) {
            super(itemView);
            // 从xml资源中初始化 ViewHolder TextView 并设置其适配器
            this.wordItemView = itemView.findViewById(R.id.word);
            this.mAdapter = mAdapter;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int mPosition = getLayoutPosition();
            String element = mWordList.get(mPosition);
            mWordList.set(mPosition, element + " Clicked");
            mAdapter.notifyDataSetChanged();
        }
    }
}
