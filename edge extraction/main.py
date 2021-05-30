from utils import get_image, output_image
from extract import roberts, sobel, laplace, scharr, prewitt, laplace_8, canny
import cv2
import matplotlib.pyplot as plt
import os


if __name__ == "__main__":
    if not os.path.exists("images"):
        os.mkdir("images")
    if not os.path.exists("fig_images"):
        os.mkdir("fig_images")
    image, h, w = get_image()

    # methods implemented by cv2
    sobelx = cv2.Sobel(image, cv2.CV_64F, 1, 0)
    sobelx = cv2.convertScaleAbs(sobelx)
    sobely = cv2.Sobel(image, cv2.CV_64F, 0, 1)
    sobely = cv2.convertScaleAbs(sobely)
    sobelxy = cv2.addWeighted(sobelx, 0.5, sobely, 0.5, 0)
    scharrx = cv2.Scharr(image, cv2.CV_64F, 1, 0)
    scharrx = cv2.convertScaleAbs(scharrx)
    scharry = cv2.Scharr(image, cv2.CV_64F, 0, 1)
    scharry = cv2.convertScaleAbs(scharry)
    scharrxy = cv2.addWeighted(scharrx, 0.5, scharry, 0.5, 0)
    laplacianimg = cv2.Laplacian(image, cv2.CV_64F, ksize=3)
    laplacian = cv2.convertScaleAbs(laplacianimg)

    sobelcv2_img, sobelcv2_path = sobelxy, "sobel-cv2.jpg"
    scharrcv2_img, scharrcv2_path = scharrxy, "scharr-cv2.jpg"
    laplacecv2_img, laplacecv2_path = laplacian, "laplace-cv2.jpg"
    cannycv2_img, cannycv2_path = cv2.Canny(image, 100, 200), "canny-cv2.jpg"

    # methods implemented in extract.py
    prewitt_img, prewitt_path = prewitt(image)
    roberts_img, roberts_path = roberts(image)
    sobel_img, sobel_path = sobel(image)
    scharr_img, scharr_path = scharr(image)
    laplace_img, laplace_path = laplace(image)
    laplace8_img, laplace8_path = laplace_8(image)
    canny_img, canny_path = canny(image)

    output_image(prewitt_img, prewitt_path)
    output_image(roberts_img, roberts_path)
    output_image(sobel_img, sobel_path)
    output_image(sobelcv2_img, sobelcv2_path)
    output_image(scharr_img, scharr_path)
    output_image(scharrcv2_img, scharrcv2_path)
    output_image(laplace_img, laplace_path)
    output_image(laplacecv2_img, laplacecv2_path)
    output_image(laplace8_img, laplace8_path)
    output_image(cannycv2_img, cannycv2_path)
    output_image(canny_img, canny_path)

    titles = ['Source Image', 'Prewitt', 'Roberts', '',
              'Sobel', 'Scharr', 'Laplace', 'Canny',
              'Sobel cv2', 'Scharr cv2', 'Laplace cv2', 'Canny cv2']
    images = [image, prewitt_img, roberts_img, laplace8_img,
              sobel_img, scharr_img, laplace_img, canny_img,
              sobelcv2_img, scharrcv2_img, laplacecv2_img, cannycv2_img]
    for i in range(12):
        if titles[i] == '':
            continue
        plt.subplot(3, 4, i + 1), plt.imshow(images[i], 'gray')
        plt.title(titles[i], y=-0.24)
        plt.xticks([]), plt.yticks([])
    plt.savefig("all-fig.png", dpi=600)
    plt.show()

    cv2.waitKey()
