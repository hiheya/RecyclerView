# 回收视图(RecyclerView)

在显示RecyclerView之前，我们需要先显示数据。所以首先需要给应用程序新建一个项目和一个数据集。在一个更复杂的应用程序中，你的数据可能来自内部存储(一个文件，SQLite 数据库，保存的首选项) ，来自另一个应用程序(联系人，照片) ，或来自互联网(云存储，谷歌表格，或任何具有 API 的数据源)；在此Demo中，需要为应用程序手动创建数据。

## 创建新项目及数据集

### 1.1 创建项目及布局

1. 新建一个项目 命名为 `RecyclerView`，选择 `Basic Activity`
2. 运行起来

### 1.2 添加代码和创建数据

1. 用一个`LinkedList`来存储数据， ["Word 1", "Word 2", "Word 3", .... ]；

---

## 创建回收视图

我们需要做的准备工作有：

- 显示数据：使用 `mWordList`；
- 一个 回收视图 (包含列表项的滚动列表)；
- 一个数据项的布局，让所有列表项看起来都一样；
- 一个布局管理器 `RecyclerView.LayoutManager` 用来处理试图元素的层次结构和布局；回收视图需要一个显式的布局管理器来管理其中包含的列表项的排列。此布局可以是垂直、水平或网格。这里我们将使用垂直的 LinearLayoutManager。
- 一个适配器： `RecyclerView.Adapter` 连接数据和 `RecyclerView`. 适配器将在 `RecyclerView.ViewHolder`当中准备数据；我们需要创建一个适配器用于在视图中插入和更新生成的单词
- 一个 `ViewHolder`: 在适配器中，我们将创建一个 `ViewHolder`，其包含`View` 信息---用于从项的布局中显示的信息。
  ![1692345265572.png](https://pic.ziyuan.wang/2023/08/18/546feef06341f.png)

### 2.1 修改 content_main.xml布局

将布局修改为：

```xml
    <androidx.recyclerview.widget.RecyclerView 
        android:id="@+id/recyclerview"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:ignore="MissingClass">
    </androidx.recyclerview.widget.RecyclerView>
```

### 2.2 为列表项创建布局

适配器需要列表中一个项的布局。所有项目使用相同的布局。这里需要在一个单独的布局资源文件中指定该列表项布局，因为它由适配器使用，而不是由回收视图使用。

1. 右键点击 **app > res > layout** 文件夹并且选择  **New > Layout resource file** .
2. 命名文件为 **wordlist_item** 并且点击  **OK** .
3. 更改 `ConstraintLayout` 为 `LinearLayout` 并使用以下属性


| **LinearLayout 属性**   | **值**           |
| ------------------------- | ------------------ |
| `android:layout_width`  | `"match_parent"` |
| `android:layout_height` | `"wrap_content"` |
| `android:orientation`   | `"vertical"`     |
| `android:padding`       | 
`"6dp"`         |

### 2.3 提取style
1. 打开 **wordlist_item.xml** .
2. 在刚刚创建的 `wordlist_item.xml`右键 `TextView` , 并且选择  **Refactor > Extract > Style** . **Extract Android Style** 这个出现的对话框.
3. 命名我们的style **word_title** 并且保留其他选项. 选择  **Launch ‘Use Style Where Possible** ' 操作. 然后点击  **OK** .
4. 出现提示时选择  **Whole Project** .
5. 找到并检查 `word_title` style 是否在  **values > styles.xml** 当中.
6. 重新打开 **wordlist_item.xml** 可以发现 `TextView` 现在已经使用了style 来代替之前的属性.

### 2.4 创建adapter
Android 使用适配器(来自 Adapter 类)将数据与列表中的 View 项连接起来。有许多不同类型的适配器可用，可以编写自定义适配器。在此Demo中，我们将创建一个适配器，该适配器将单词列表与单词列表视图项关联起来。
要将数据与视图项连接起来，适配器需要了解视图项。适配器使用了一个 ViewHolder，它描述了一个 View 项及其在回收视图中的位置。
首先，我们将构建一个适配器，用于在单词列表中的数据和显示它的回收视图之间建立桥梁:
1. 新建一个`WordListAdapter`
```java
public class WordListAdapter extends
        RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    // 用于保存数据，实例化一个LinkedList 字符类型为String
    private final LinkedList<String> mWordList;
    // LayoutInflater 读取布局 XML 描述并将其转换为相应的 View 项。
    private LayoutInflater mInflater;

    // 构造函数需要一个上下文参数，以及一个包含应用程序数据的单词链表
    // 这个方法需要实例化一个用于 mFlater 的 LayoutFlater，并将 mWordList 设置为传入的数据:

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
     * 3. 操作和显示试图
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
```
### 2.5 为adapter创建一个ViewHolder
- 在 `WordListAdapter ` 中新建一个内部类 `WordViewHolder `;
- 为 `TextView` 和适配器的 `WordViewHolder` 内部类添加变量;
- 在内部类 `WordViewHolder` 中，添加一个构造函数，该构造函数从 `word` XML 资源初始化 `ViewHolder` `TextView`，并设置其适配器;
### 2.6 在适配器中存储数据
- 需要将数据保存在适配器中，而 `WordListAdapter` 需要一个构造函数来从数据初始化 `mWordList`;
- 