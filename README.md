# 为RecyclerView设置相同的item间距（自定义ItemDecoration）

项目中经常碰到列表当中的每一项之间需要设置间距的问题，我们可以通过给列表中的每一项的根布局设置margin值来实现，例如需要纵向的间距时，可以给每一项设置right_margin，这种方法下，整个列表的最左边没有边距，而最右边有一个right_margin。当然，可以给整个列表设置与right_margin同样大小的left_margin来实现对称。但是，这样的方法并不优雅、不全面，比如如果这个列表左右两边不需要边距，这样的方法是实现不了的。

对于RecyclerView来说，我们可以自定义ItemDecoration，为item之间设置间距。想要item间的纵向间距都相同，通常的做法也类似于上面的做法，在getItemOffsets方法中给每一行的每个item的outRect设置相同的`right`，在这一行的最后一项不设置right或者给第一项设置left。这样的方法，比上面的方法优雅一些，因为可以判断一行当中第一项或者最后一项，并作区分处理。但是，在ItemDecoration方法中通过outRect设置的间距，都是占用item本身的空间，这个方法会导致一行的最后一项（没设置right的一项）或第一项（多设置left的一项）和其他项宽度不一致。所以我们来探索新的方式，使每个item的宽度维持一致并且间距也相同。

这里面需要推算一下。假设我们要处理的是纵向滚动的列表，每一行的item个数为spanCount，item间的间距（纵向）是space。则这一行中除去item占用的空间，剩下的空间为（假设最左边和最右边都有间距）： 

`totalSpace=(spanCount+1)*space`

这样，为了保证每个item的宽度相同且间距相同，每一个item所需要提供给间距的宽度是

```
itemSpace = totalSpace/spanCount
= (spanCount+1)*space/spanCount
= space + space/spanCount
```

对于一行当中的第一个item，左边的间距是

`left1 = space`

可以计算出右边的间距是

```
right1 = itemSpace - left1
= space + space/spanCount - space
= space/spanCount
```

对于一行当中的第二个item，左边的间距是

```
left2 = space - right1
= space - space/spanCount
= space*(spanCount - 1)/spanCount
```

右边的边距是

```
right2 = itemSpace - left2
= space + space/spanCount - (space - space/spanCount)
= 2*space/spanCount
```

对于第三个item，左边的间距是

```
left3 = space - right2
= space - 2*space/spanCount
= space*(spanCount - 2)/spanCount
```

右边的边距是

```
right3 = itemSpace - left3
= space + space/spanCount - (space - 2*space/spanCount)
= 3*space/spanCount
```

这样我们可以发现一些规律，假设一行当中item的索引为spanIndex（从0开始），则对于这个item来说，左边的间距是

```
left = space*(spanCount - spanIndex)/spanCount
```

右间距是

```
right = (spanIndex + 1)*space/spanCount
```

对于RecyclerView来说，spanIndex和spanCount都可以获取到。


GridLayoutManager:

```
GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
spanIndex = lp.getSpanIndex();
GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
spanCount = layoutManager.getSpanCount();
```

StaggeredGridLayoutManager:

```
StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
spanIndex = lp.getSpanIndex();
StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) parent.getLayoutManager();
spanCount = layoutManager.getSpanCount(); 
```

LinearLayoutManager:

```
spanIndex = 0;
spanCount = 1;
```

同理，我们可以推出很多其他情况，例如横向排列，例如去掉最边上的间距，例如item占据的列不止一列……

根据上面的理论，我实现了一个自定义ItemDecoration：SCommonItemDecoration，包含了上面及其他所有情况，可以设置横向和纵向列表的间距，同时可以设置边缘是否也设置间距。支持LinearLayoutManager，GridLayoutManager和StaggeredGridLayoutManager，也支持不同的orientation。同时，SCommonItemDecoration还支持为不同的item type设置不同的间距。

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


