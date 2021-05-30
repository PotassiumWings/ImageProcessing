环境 python 3.8.8

```
pip install -r requirements.txt
python main.py
```

即可得到 part1-fig part2-fig all-fig 三张图片，对应小论文中的对比图片和 Laplacian 锐化图片。images 和 fig-images 中的图片为更加详细的输出图片，其中 images/*_canny.jpg 为使用不同高低阈值的 Canny 算法得到的边缘信息。

