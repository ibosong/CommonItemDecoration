# CommonItemDecoration
Set width or height of dividers between items of RecyclerView with item type

项目中经常碰到列表当中的每一项之间需要设置间距的问题，我们可以通过给列表中的每一项设置margin值来实现，例如纵向的间距可以给每一项设置right_margin，这种方法下，整个列表的最左边会紧贴屏幕边缘，而最右边有一个right_margin。你说，可以给整个列表设置同样大小的left_margin来实现对称。对的，这样是可以的。

但是，完美主义的我认为这样的方法是不优雅的，不全面的！

遂为RecyclerView实现了一个自定义ItemDecoration：SCommonItemDecoration，我们可以设置横向和纵向的间距，同时可以设置边缘是否也设置间距（上下边缘间距等于横向间距，左右边缘间距等于纵向间距）。支持LinearLayoutManager，GridLayoutManager和StaggeredGridLayoutManager，也支持横向或纵向，随便你怎么设。你说，这个很简单啊，在getItemOffsets方法中给每个item的outRect设置相同的`right`，在最后一项的时候不设置right就行了，这样每个item间的间距都是一样的。是的，这样可以。

但是，这会有个问题，就是最后一个item的宽（或高）和其他项不一致！因为设置的outRect是占用item本身的空间的。

所以，我这个SCommonItemDecoration的优势，就是不但间距相同，每个item的宽（或高）也相同。同时，为了让SCommonItemDecoration更有意义，我还实现了为不同的item type设置不同的间距，颇费。

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

![](demo.gif)


