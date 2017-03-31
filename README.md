# CommonItemDecoration
Set width or height of divders between items of RecyclerView with item type

项目中经常碰到列表当中的每一项之间需要设置间距的问题，我们可以通过给列表中的每一项设置margin值来实现，例如纵向的间距可以给每一项设置right_margin，这种方法下，整个列表的最左边会紧贴屏幕边缘，而最右边有一个right_margin。你说，可以给整个列表设置同样大小的left_margin来实现对称。对的，这样是可以的。

但是，完美主义的我认为这样的方法是不优雅的！

遂为RecyclerView实现了一个自定义ItemDecoration，我们可以设置横向和纵向的间距，同时可以设置边缘是否也设置间距（上下边缘间距等于横向间距，左右边缘间距等于纵向间距）。同时，我们还可以为不同的item type设置不同的间距，完美的不要不要的！支持LinearLayoutManager，GridLayoutManager和StaggeredGridLayoutManager，随便你怎么设。

### 用法

先构建一个SCommonItemDecoration.ItemDecorationProps的SparseArray列表，

```
SparseArray<SCommonItemDecoration.ItemDecorationProps> propMap = new SparseArray<>();
```

为其添加项目，其中的每一项是一个item type对应一个ItemDecorationProps对象，ItemDecorationProps包含属性：

```
private int verticalSpace; // 纵向间距
private int horizontalSpace; // 横向间距
private boolean hasVerticalEdge; // 是否给左右边缘设置间距
private boolean hasHorizontalEdge; // 是否给上下边缘设置间距
```

然后构建SCommonItemDecoration对象并设置给RecyclerView

```
mBrandRecyclerView.addItemDecoration(new SCommonItemDecoration(propMap));
```


不多说了，不懂的看sample吧。