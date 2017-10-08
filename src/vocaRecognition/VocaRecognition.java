// package vocaRecognition;
//
// import java.util.List;
//
// import org.rosuda.JRI.Rengine;
//
// import rSupport.UniqueRengine;
//
// public class VocaRecognition {
// public static void naiveFeatureFetch(boolean[][] image, int[][] rects, int
// dataWidth, int dataHeight) {
// Rengine rengine = UniqueRengine.getRengine();
// for (int[] rect : rects) {
// int xBaseRect = rect[0];
// int yBaseRect = rect[1];
// int width = rect[2];
// int height = rect[3];
// List<Integer> splitPos = VocaSpliter.naiveCharDetection(image, rect, 5, 255);
//
// int base = -1;
// int limit = 0;
// for (int i = 0; i < splitPos.size(); ++i) {
// base = limit;
// limit = xBaseRect + splitPos.get(i);
// if (limit - base > dataWidth || height > dataHeight) {
// System.err.println("Split at pos:x,y is too big to fit in a data");
// } else {
// fetchRegCharFeature(image, base, yBaseRect, limit, yBaseRect + height,
// dataWidth, dataHeight);
// }
// }
// }
// }
//
// public static boolean[] fetchRegCharFeature(boolean[][] image, int xBase, int
// yBase, int xLimit, int yLimit,
// int dataWidth, int dataHeight) {
// boolean[] data = new boolean[dataHeight * dataWidth];
// for (int y = yBase; y < dataHeight; ++y) {
// for (int x = xBase; x < dataWidth; ++x) {
// if (x < xLimit && y < yLimit) {
// data[(y - yBase) * (x * xBase)] = image[y][x];
// } else {
// data[(y - yBase) * (x * xBase)] = false;
// }
// }
// }
// return data;
// }
// }
