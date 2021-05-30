import cv2
import numpy as np
from matplotlib import pyplot as plt


def get_image():
    image = cv2.imread("lena.jpg", 0)
    (h, w) = image.shape
    return image, h, w


def output_image(image, path="output.jpg"):
    cv2.imwrite("images/" + path, image)


def dot(image, g):
    # result = cv2.filter2D(image, -1, g, anchor=(0, 0))
    (w, h) = (len(image), len(image[0]))
    (gw, gh) = (len(g), len(g[0]))
    result = np.zeros((w, h))
    for i in range(0, h - gh):
        for j in range(0, w - gw):
            mx_i = min(i + gh, h)
            mx_j = min(j + gw, w)
            result[i][j] = np.sum(image[i:mx_i, j:mx_j] * g[0:mx_i - i, 0:mx_j - j])
    return result.astype(np.int32)


def value_range(x):
    if x >= 255:
        return 255
    if x <= 0:
        return 0
    return int(round(x))


np_value_range = np.vectorize(value_range)


def normalize(img, do_equalize=False):
    if do_equalize:
        return equalize(img)
    max_value = np.max(img)
    min_value = np.min(img)
    return ((img - min_value) / (max_value - min_value) * 255).astype(np.uint8)


def equalize(img):
    cnt = [0 for i in range(256)]
    for i in range(0, len(img)):
        for j in range(0, len(img[0])):
            cnt[img[i][j]] += 1

    for i in range(1, 256):
        cnt[i] += cnt[i - 1]

    res = np.zeros((len(img), len(img[0])))
    for i in range(0, len(img)):
        for j in range(0, len(img[0])):
            res[i][j] = cnt[img[i][j]] / cnt[255] * 255
    return res


# plt show
show_images = []
titles = []


def show(img, title=""):
    global show_images
    global titles
    show_images.append(img)
    titles.append(title)


def save(path):
    global show_images
    global titles
    cnt = len(show_images)
    now = 1
    if cnt > 4:
        lines = int(np.ceil(cnt / 4))
        cnt = 4
    else:
        lines = 1
    for image in show_images:
        plt.subplot(lines, cnt, now)
        plt.title(titles[now - 1], y=-0.3)
        plt.xticks([]), plt.yticks([])
        plt.imshow(image)
        now += 1
    plt.savefig("fig_images/" + path, dpi=600)
    plt.show()
    show_images = []
    titles = []
