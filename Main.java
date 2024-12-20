import java.util.ArrayList;
import java.util.Random;

public class Main {
    /**
     * 主函数，程序的入口点
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 初始化总用牌数
        long sumsumcard = 0;
        // 初始化平均用牌数
        double averagecard = 0;
        // 初始化当前用牌数
        int sumcard = 0;
        // 初始化迭代次数
        int time = 100000;
        // 初始化内层迭代次数
        int time1 = 1000000;
        // 初始化临时变量
        int temp = 0;
        // 初始化夹断次数
        int numOfDie = 0;
        // 初始化平均夹断概率
        double averageOfDie = 0;
        // 初始化另一个夹断次数
        int numOfDie1 = 0;
        // 初始化另一个平均夹断概率
        double averageOfDie1 = 0;

        // 将 time 和 time1 转换为科学计数法的字符串表示
        String scientificNotationTime = String.format("%.0e", (double) time);
        String scientificNotationTime1 = String.format("%.0e", (double) time1);

        // 创建一个 Random 对象，用于生成随机数
        Random rand = new Random();

        // 打印本次实验的参数设置
        System.out.println("本次实验每次卡牌迭代" + scientificNotationTime1 + "次，模拟卡牌迭代" + scientificNotationTime + "次");
        System.out.println("");

        // 遍历不同的卡牌数量限制
        for (int cardNumLimit = 2; cardNumLimit < 6; cardNumLimit++) {
            // 遍历不同的醉意等级
            for (int level = 0; level < 5; level++) {
                // 如果醉意等级为 4，则用牌数期望为无穷大，夹断概率为 0%
                if (level == 4) {
                    System.out.println("有" + cardNumLimit + "张卡牌位时醉意等级为" + level + "的杜康用牌数期望为无穷大，20张牌前夹断概率为0%，10张牌前夹断概率为0%");
                    break;
                }
                // 进行 time 次模拟
                for (int j = 0; j < time; j++) {
                    // 创建一个 ArrayList 来存储初始卡牌
                    ArrayList<Integer> initialCard = new ArrayList<>();
                    // 初始卡牌为 0 为无，1 为红，2 为黄，3 为绿
                    for (int i = 0; i < 2; i++) {
                        // 随机生成初始卡牌
                        temp = rand.nextInt(3);
                        initialCard.add(temp + 1);
                    }
                    // 进行 time1 次卡牌迭代
                    for (int i = 0; i < time1; i++) {
                        // 如果卡牌用完，则记录夹断次数
                        if (initialCard.size() == 0) {
                            if (sumcard < 20) {
                                numOfDie++;
                            }
                            if (sumcard < 10) {
                                numOfDie1++;
                            }
                            break;
                        }
                        // 根据当前的醉意等级和卡牌数量限制来抽取新的卡牌
                        drawCard(initialCard, level, cardNumLimit);
                        // 累计用牌数
                        sumcard++;
                    }
                    // 累计总用牌数
                    sumsumcard += sumcard;
                    // 重置当前用牌数
                    sumcard = 0;
                }
                // 计算平均用牌数
                averagecard = (double) sumsumcard / time;
                // 计算夹断概率
                averageOfDie = (double) numOfDie / time * 100;
                averageOfDie1 = (double) numOfDie1 / time * 100;
                // 将平均用牌数和夹断概率格式化为两位小数的字符串
                String avg = String.format("%.2f", averagecard);
                String avg1 = String.format("%.2f", averageOfDie);
                String avg2 = String.format("%.2f", averageOfDie1);
                // 打印当前条件下的统计数据
                System.out.println("有" + cardNumLimit + "张卡牌位时醉意等级为" + level + "的杜康用牌数期望为" + avg + "，20张牌前夹断概率为" + avg1 + "%" + "，10张牌前夹断概率为" + avg2 + "%");
                // 重置总用牌数
                sumsumcard = 0;
                // 重置夹断次数
                numOfDie = 0;
                // 重置另一个夹断次数
                numOfDie1 = 0;
            }
            System.out.println("");
        }
    }
    /**
     * 从初始卡牌列表中打出一张卡牌，并根据打出的卡牌和当前醉意等级添加新的卡牌
     *
     * @param initialCard 初始卡牌列表
     * @param level       当前醉意等级
     * @param cardNumLimit 卡牌数量限制
     */
    public static void drawCard(ArrayList<Integer> initialCard, int level, int cardNumLimit) {
        // 定义一个变量 cardToRemove，用于存储要移除的卡牌
        Integer cardToRemove = null;
        // 如果 initialCard 中包含 2，则将 2 赋值给 cardToRemove
        if (initialCard.contains(2)) {
            cardToRemove = 2;
            // 否则，如果 initialCard 中包含 3，则将 3 赋值给 cardToRemove
        } else if (initialCard.contains(3)) {
            cardToRemove = 3;
            // 否则，如果 initialCard 中包含 1，则将 1 赋值给 cardToRemove
        } else if (initialCard.contains(1)) {
            cardToRemove = 1;
        }

        // 如果 cardToRemove 不为空，则执行以下操作
        if (cardToRemove != null) {
            // 从 initialCard 中移除 cardToRemove
            initialCard.remove(cardToRemove);
            // 如果 cardToRemove 等于 1，则执行 cardAddCard 方法
            if (cardToRemove == 1) {
                cardAddCard(initialCard, cardNumLimit);
            }
            // 执行 buffAddCard 方法
            buffAddCard(initialCard, level, cardNumLimit);
        }
    }

    /**
     * 根据当前醉意等级向初始卡牌列表中添加新的卡牌
     *
     * @param initialCard 初始卡牌列表
     * @param level       当前醉意等级
     * @param cardNumLimit 卡牌数量限制
     */
    public static void buffAddCard(ArrayList<Integer> initialCard, int level, int cardNumLimit) {
        // 如果 initialCard 的大小小于 cardNumLimit，则执行以下操作
        if (initialCard.size() < cardNumLimit) {
            // 创建一个 Random 对象，用于生成随机数
            Random rand = new Random();
            // 生成一个随机数 temp，其值在 0 到 3 之间
            int temp = rand.nextInt(4);
            // 如果 temp 小于 level，则向 initialCard 中添加一个 1
            if (temp < level) {
                initialCard.add(1);
            }
        }
    }
    /**
     * 使用红杜康后根据卡牌数量限制向初始卡牌列表中添加新的卡牌
     *
     * @param initialCard 初始卡牌列表
     * @param cardNumLimit 卡牌数量限制
     */
    public static void cardAddCard(ArrayList<Integer> initialCard, int cardNumLimit) {
        // 如果 initialCard 的大小小于 cardNumLimit，则执行以下操作
        if (initialCard.size() < cardNumLimit) {
            // 创建一个 Random 对象，用于生成随机数
            Random rand = new Random();
            // 生成一个随机数 temp，其值在 0 到 4 之间
            int temp = rand.nextInt(5);
            // 根据 temp 的值向 initialCard 中添加相应的卡牌
            switch (temp) {
                case 0, 1:
                    initialCard.add(1);
                    break;
                case 2, 3:
                    initialCard.add(2);
                    break;
                case 4:
                    initialCard.add(3);
                    break;
            }
        }
    }

}