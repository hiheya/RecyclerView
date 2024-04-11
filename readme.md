# 回收视图(RecyclerView)

## 关于回收视图

当在可滚动列表中显示大量项时，大多数项都不可见。例如，在一个单词或新闻标题的长列表中，用户一次只能看到几个条目。或者，我们可能拥有一个随着用户与其交互而变化的数据集。如果每次数据更改时都创建一个新的视图，那么即使对于小数据集，也需要创建大量的视图项。
从性能角度来看，我们希望节省内存和时间:

- 若要节省内存，需要尽量减少任何给定点上存在的 View 项的数量。
- 若要节省时间，需要尽量减少必须创建的“视图”项的数量。

要实现这两个目标，创建多于用户在屏幕上可以看到的视图项，并缓存创建的视图。然后，当列表项滚动进出显示时，重用具有不同数据的
视图。
回收视图类是 ListView 的一个更高级和更灵活的版本。它是一个容器，通过维护有限数量的视图项，可以有效地显示大型、可滚动的数据集。
如果需要显示大量可滚动数据，或者需要显示其元素在运行时根据用户操作或网络事件发生更改的数据集合，请使用回收视图。
在显示RecyclerView之前，我们需要先显示数据。所以首先需要给应用程序新建一个项目和一个数据集。在一个更复杂的应用程序中，你的数据可能来自内部存储(
一个文件，SQLite 数据库，保存的首选项) ，来自另一个应用程序(联系人，照片) ，或来自互联网(云存储，谷歌表格，或任何具有
API 的数据源)；在此Demo中，需要为应用程序手动创建数据。

### 回收视图组件

#### Data

任何可显示的数据都可以在回收视图中显示。

- Text
- Images
- Icons

数据可以来自任何来源。

- 由应用程序创建。例如，一个打乱单词的游戏。
- 来自本地数据库。例如，联系人列表。
- 从云存储或互联网。例如新闻标题。

#### RecyclerView

一个回收视图是一个可滚动容器的视图组。它是类似项目的长列表的理想选择。

回收视图使用数量有限的视图项，这些项在离开屏幕时会被重用。这样可以节省内存，并使用户在滚动数据时更新列表项变得更快，因为不必为每个出现的项创建新的
View。

一般来说，回收视图在屏幕上保留尽可能多的视图项，在列表的两端各加上一些额外的视图项，以确保滚动是快速和平滑的。

#### Item Layout

列表项的布局保存在一个单独的 XML 布局文件中，以便适配器可以创建视图项并独立于活动的布局编辑其内容。

#### Layout Manager

布局管理器将 View 项放置在 ViewGroup 中，比如回收视图，并确定何时reuse用户不再可见的 View
项。为了reuse(或回收)视图，布局管理器可能会要求适配器用来自数据集的不同元素替换视图的内容。
以这种方式回收 View 项可以避免创建不必要的 View 项或执行昂贵的 findViewById ()查找，从而提高性能。
回收视图提供了这些内置的布局管理器:

- LinearLayoutManager 显示垂直或水平滚动列表中的项。
- GridLayoutManager 显示网格中的项。
- StaggeredGridLayoutManager 在交错网格中显示项。

若要创建自定义布局管理器，需要扩展 `RecycleView.LayoutManager` 类。

#### Animations

默认情况下，用于添加和删除项目的动画在回收视图中启用。若要自定义这些动画，请继承RecycleView.ItemAnimator
类并使用RecycleView.setItemAnimator()方法。

#### Adapter

适配器帮助两个不兼容的接口协同工作。在回收视图中，适配器将数据与item项连接起来。它充当数据和view之间的中介。适配器接收或检索数据，完成使其在view
中可显示所需的任何工作，并将数据放置在视图中。
例如，适配器可以从数据库中接收数据作为光标对象，提取单词及其定义，将它们转换为字符串，并将字符串放在具有两个
TextView 元素的 View 项中---一个用于单词，一个用于定义。
RecyclerView.Adapter 实现自 ViewHolder，并且必须重写以下回调:
RecyclerView.Adapter是一个抽象类，用于在RecyclerView中显示数据。它需要实现以下三个方法：

- `onCreateViewHolder()`
  ：当RecyclerView需要新的ViewHolder来表示一个项目时，此方法会被调用。它会将一个View项目膨胀并返回一个包含它的新ViewHolder。ViewHolder是一个用于存储对列表项中使用的视图的引用的类。
- `onBindViewHolder()`
  ：此方法在RecyclerView需要绑定数据到ViewHolder时调用，例如当新的View项目滚动到屏幕上时。它会在给定的RecyclerView位置设置View项目的内容。
- `getItemCount()`：此方法返回适配器持有的数据集中的总项目数。
  简单来说，这些方法一起工作，以在RecyclerView中有效地显示数据。
  onCreateViewHolder()负责为每个项目创建视图，onBindViewHolder()
  负责将数据绑定到这些视图，getItemCount()则告诉RecyclerView有多少个项目可以显示。
  这种模式允许RecyclerView有效地回收和重用已滚动过的视图，从而提高性能。

#### ViewHolder

RecyclerView.ViewHolder描述了一个View项目及其在RecyclerView中的位置的元数据。每个ViewHolder都持有一组数据。适配器将数据添加到每个ViewHolder，以供布局管理器显示。
你可以在XML资源文件中定义你的ViewHolder布局。它可以包含（几乎）任何类型的View，包括可点击的元素。
简单来说，ViewHolder是一个包含列表项的视图和元数据的容器。元数据提供了关于项目在RecyclerView中位置的信息。
你可以在XML资源文件中定义ViewHolder的布局，并且它可以包含几乎任何类型的View，包括可点击的元素。这意味着你可以创建复杂的列表项，具有交互性和动态内容。

## 创建新项目及数据集

### 实现步骤

在Android开发中实现RecyclerView的步骤。以下是具体的步骤：

- 添加RecyclerView依赖：如果需要的话（取决于用于Activity的模板），添加RecyclerView的依赖。
- 将RecyclerView添加到Activity布局：在你的Activity布局文件中添加一个RecyclerView。
- 为一个View项目创建一个布局XML文件：创建一个布局XML文件，该文件定义了你的列表项的外观。
- 扩展RecyclerView.Adapter并实现onCreateViewHolder()和onBindViewHolder()
  方法：创建一个新的类，该类扩展了RecyclerView.Adapter。你需要实现onCreateViewHolder()
  （创建新的ViewHolder）和onBindViewHolder()（将数据绑定到ViewHolder）方法。
-

扩展RecyclerView.ViewHolder以创建你的项目布局的ViewHolder：创建一个新的类，该类扩展了RecyclerView.ViewHolder。你可以通过覆盖onClick()
方法来添加点击行为。

- 在Activity中，创建一个RecyclerView并用适配器和布局管理器初始化它：在Activity的onCreate()
  方法中，创建一个RecyclerView实例，并使用你的适配器和一个布局管理器（例如LinearLayoutManager或GridLayoutManager）来初始化它。
  这些步骤一起工作，以在RecyclerView中有效地显示数据。

### 1.1 创建项目及布局

1. 新建一个项目 命名为 `RecyclerView`，选择 `Basic Activity`
2. 运行起来

### 1.2 添加代码和创建数据

1. 用一个`LinkedList`来存储数据， ["Word 1", "Word 2", "Word 3", .... ]；

---

## 创建回收视图

我们需要做的准备工作有：

- 显示数据（Data）：使用 `mWordList` 数据来自哪里并不重要。可以像在实践中那样在本地创建数据，像在以后的实践中那样从设备上的数据库获取数据，或者从云中提取数据；
- 一个 回收视图（RecyclerView） (包含列表项的滚动列表。在 Activity
  布局文件中定义的回收视图实例，用作视图项的容器。)；
- 一个数据项的布局（Layout for one item of
  data），让所有列表项看起来都一样，因此可以对它们使用相同的布局。项目布局必须与活动布局分开创建，以便每次可以创建一个视图项并用数据填充；
- 一个布局管理器（Layout Manager） `RecyclerView.LayoutManager` 用来处理视图元素的层次结构和布局，每个
  ViewGroup 都有一个布局管理器。回收视图需要一个显式的布局管理器来管理其中包含的列表项的排列此布局可以是垂直、水平或网格。布局管理器是
  RecyclerView.LayoutManager 的一个实例。这里我们将使用垂直的 LinearLayoutManager。
- 一个适配器（Adapter）： `RecyclerView.Adapter` 连接数据和 `RecyclerView`.
  适配器将在 `RecyclerView.ViewHolder`当中准备数据；使用回收视图.Adapter 的扩展将数据连接到回收视图。它准备数据以及如何在
  ViewHolder 中显示。当数据发生更改时，适配器将更新回收视图中相应列表项视图的内容。我们需要创建一个适配器用于在视图中插入和更新生成的单词
- 一个 `ViewHolder`: 在适配器中，我们将创建一个 `ViewHolder`，其包含`View`
  信息---用于从项的布局中显示的信息。在RecyclerView中，ViewHolder是一个持有单个列表或网格项视图的信息的类。
  当我们在RecyclerView中显示数据时，每个列表项都需要一个布局，这个布局可能包含图片、文字等各种元素。ViewHolder的任务就是存储这些元素的引用，以便我们可以在需要时快速访问它们，而不需要每次都去查找它们。
  通常，我们会创建一个扩展自RecyclerView.ViewHolder的类，这个类会包含列表项布局中所有需要动态设置内容的元素的引用。

![1692345265572.png](https://pic.ziyuan.wang/2023/08/18/546feef06341f.png)

### 2.1 修改 content_main.xml布局

将布局修改为：

```xml

<androidx.recyclerview.widget.RecyclerView android:id="@+id/recyclerview"
    android:layout_width="match_parent" android:layout_height="match_parent"
    tools:ignore="MissingClass"></androidx.recyclerview.widget.RecyclerView>
```

### 2.2 为列表项创建布局

适配器需要列表中一个项的布局。所有项目使用相同的布局。这里需要在一个单独的布局资源文件中指定该列表项布局，因为它由适配器使用，而不是由回收视图使用。

1. 右键点击 **app > res > layout** 文件夹并且选择  **New > Layout resource file** .
2. 命名文件为 **wordlist_item** 并且点击  **OK** .
3. 更改 `ConstraintLayout` 为 `LinearLayout` 并使用以下属性

| **LinearLayout 属性**     | **值**            |
|-------------------------|------------------|
| `android:layout_width`  | `"match_parent"` |
| `android:layout_height` | `"wrap_content"` |
| `android:orientation`   | `"vertical"`     |
| `android:padding`       | `"6dp"`          |

### 2.3 提取style

1. 打开 **wordlist_item.xml** .
2. 在刚刚创建的 `wordlist_item.xml`右键 `TextView` , 并且选择  **Refactor > Extract > Style** . *
   *Extract Android Style** 这个出现的对话框.
3. 命名我们的style **word_title** 并且保留其他选项. 选择  **Launch ‘Use Style Where Possible** ' 操作.
   然后点击  **OK** .
4. 出现提示时选择  **Whole Project** .
5. 找到并检查 `word_title` style 是否在  **values > styles.xml** 当中.
6. 重新打开 **wordlist_item.xml** 可以发现 `TextView` 现在已经使用了style 来代替之前的属性.

### 2.4 创建adapter

Android 使用适配器(来自 Adapter 类)将数据与列表中的 View
项连接起来。有许多不同类型的适配器可用，可以编写自定义适配器。在此Demo中，我们将创建一个适配器，该适配器将单词列表与单词列表视图项关联起来。
要将数据与视图项连接起来，适配器需要了解视图项。适配器使用了一个 ViewHolder，它描述了一个 View
项及其在回收视图中的位置。
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
    public WordListAdapter(Context context, LinkedList<String> mWordList) {
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
        View mItemView = mInflater.inflate(R.layout.wordlist_item, parent, false);
        return new WordViewHolder(mItemView, this);
    }

    /**
     *
     * OnBindViewHolder () 方法将数据连接到视图持有者，在RecyclerView中将给定位置的数据与 ViewHolder 关联。
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        String mCurrent = mWordList.get(position);
        holder.wordItemView.setText(mCurrent);
    }

    // 返回mWordList.size()来getItemCount 返回可用于显示的数据项的数量。
    @Override
    public int getItemCount() {
        return mWordList.size();
    }

    // 实现 ViewHolder 类
    // item 布局的 ViewHolder 类通常定义为适配器的内部类。继承自`RecyclerView.ViewHolder`来创建 ViewHolder。可以通过重写 onClick ()方法添加单击行为。
    // 如果要添加点击处理事件，需要实现View.onClickListener接口 还有一种方法是让 ViewHolder 实现 View.onClickListener 方法。
    public class WordViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // 内部类TextView 和 WordListAdapter变量
        public final TextView wordItemView;
        final WordListAdapter mAdapter;

        /**
         *
         *
         * @param itemView 用于显示数据
         * @param mAdapter 用于管理data和view
         */
        // 在其构造函数中，ViewHolder 必须inflate其布局，与其adapter关联，并在适用的情况下设置单击listener。
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

- 在 `WordListAdapter ` 中新建一个内部类 `WordViewHolder `; item布局的 ViewHolder
  类通常定义为适配器的内部类继承自RecycleView用于创建 ViewHolder。可以通过重写 onClick ()方法添加单击行为。
    - ViewHolder类通常定义为适配器的内部类：这是因为ViewHolder类需要访问适配器的数据。将ViewHolder类定义为适配器的内部类可以使其直接访问适配器的成员变量和方法。
    -
  扩展RecyclerView.ViewHolder以创建ViewHolder：你需要创建一个新的类，该类扩展了RecyclerView.ViewHolder。这个新类将代表你的列表项的布局，并包含了列表项中的视图引用。
    - 通过覆盖onClick()
      方法添加点击行为：如果你想让你的列表项响应点击事件，你可以在你的ViewHolder类中覆盖onClick()
      方法。在这个方法中，你可以定义当用户点击列表项时应该发生什么。
  ```java
  class WordViewHolder extends RecyclerView.ViewHolder {
  ```
- 如果要添加单击处理，则需要实现 View.onClickListener。一种方法是让 ViewHolder 实现
  View.onClickListener 方法。

```java
public WordViewHolder(View itemView,WordListAdapter adapter){
        super(itemView);
        wordItemView=itemView.findViewById(R.id.word);
        this.mAdapter=adapter;
        itemView.setOnClickListener(this);
        } 
```

- 为 `TextView` 和适配器的 `WordViewHolder` 内部类添加变量;

```java
public final TextView wordItemView;
final WordListAdapter mAdapter; 
```

- 在其构造函数中，ViewHolder 必须inflate其布局，与其适配器关联，并在适用的情况下设置单击侦听器。

```java
public WordViewHolder(View itemView,WordListAdapter mAdapter){
        super(itemView);
        // 从xml资源中初始化 ViewHolder TextView 并设置其适配器
        this.wordItemView=itemView.findViewById(R.id.word);
        this.mAdapter=mAdapter;
        itemView.setOnClickListener(this);
        } 
```

- 而且，如果实现了 View.onClickListener，还必须实现 onClick ()方法。

```java
@Override
public void onClick(View v){
        int mPosition=getLayoutPosition();
        String element=mWordList.get(mPosition);
        mWordList.set(mPosition,element+" Clicked");
        mAdapter.notifyDataSetChanged();
        }
```

- 如果希望将单击侦听器附加到 ViewHolder 的其他元素，需要在 onBindViewHolder ()中动态执行此操作

- 在内部类 `WordViewHolder` 中，添加一个构造函数，该构造函数从 `word` XML
  资源初始化 `ViewHolder` `TextView`，并设置其适配器;

### 2.6 创建RecycleView

1. 定义一个RecyclerView.

```java
private RecyclerView mRecyclerView; 
```

2. 在 Activity中的 onCreate ()方法中，获取布局中回收视图的句柄

```java
mRecyclerView=findViewById(R.id.recyclerview); 
```

3. 创建适配器并提供要显示的数据

```java
 mAdapter = new WordListAdapter(this, mWordList);
```

4. 用RecycleView连接适配器

```java
mRecyclerView.setAdapter(mAdapter);
```

5. 给RecycleView一个默认的布局管理器。

```java
mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
```

- RecyclerView是显示滚动列表数据的有效方式：RecyclerView是Android提供的一个组件，它可以高效地显示大量的滚动列表数据。它通过回收和重用已经滚动出屏幕的视图来实现这种效率。 
- 它使用适配器模式将数据与列表项视图连接：适配器模式是一种设计模式，它允许将一种接口转换为另一种接口，使得原本由于接口不兼容而不能一起工作的类可以一起工作。在这里，适配器是一个连接数据和列表项视图的桥梁。 
- 要实现一个RecyclerView，你需要创建一个适配器和一个ViewHolder：适配器负责从数据源获取数据，并将数据绑定到ViewHolder中。ViewHolder则负责存储列表项视图的引用，以便适配器可以更新视图的内容。 
- 你还需要创建那些获取数据并将其添加到列表项的方法：这通常是指在适配器中实现的onBindViewHolder()方法，它负责将数据绑定到ViewHolder的视图中。