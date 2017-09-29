showDist <- function(vector,path){
  jpeg(file=path)
  bks <- seq(from=0,to=255,by=1)
  plot(hist(vector,breaks = bks))
  dev.off()
}