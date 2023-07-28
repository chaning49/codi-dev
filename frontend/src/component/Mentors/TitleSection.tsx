import FlexBox from "@/ui/atoms/FlexBox";
import Typography from "@/ui/atoms/Typography";
import theme from "@/ui/theme";

const TitleSection = ({
  logo,
  title,
  description,
}: {
  logo: JSX.Element;
  title: string;
  description?: string;
}) => (
  <FlexBox
    direction="column"
    rowGap="10px"
    alignItems="center"
    {...{ marginBottom: "40px" }}
  >
    {logo}
    <Typography
      variant="h1"
      size={theme.fonts.size.lg}
      weight={theme.fonts.weight.black}
    >
      {title}
    </Typography>
    {description && (
      <Typography
        variant="div"
        size={theme.fonts.size.sm}
        weight={theme.fonts.weight.regular}
      >
        {description}
      </Typography>
    )}
  </FlexBox>
);
export default TitleSection;
